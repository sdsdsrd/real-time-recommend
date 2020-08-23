#!/usr/bin/env python
# -*- coding: utf-8 -*-

from six import iteritems


class Config(dict):
    """The core configuration class.

    Example usage:

        # load default configurations from a dictionary (or an object)
        config = Config({'DEBUG': True})
        assert config['DEBUG'] == True
        assert config.DEBUG == True

        # load/update configurations from a dictionary
        config.from_mapping({'PORT': 5000})
        # or the shortcut
        config.load({'PORT': 5000})

        # load/update configurations from an object
        from yourapplication import default_config
        config.from_object(default_config)
        # or the shortcut
        config.load(default_config)

        # load configurations from environment variables
        import os
        config = Config(datasrc=os.environ)
        assert 'DEBUG' not in config
        os.environ.setdefault('SECRET_KEY', '123***456')
        assert config.SECRET_KEY == '123***456'

    Note: only uppercase keys will be added as config.
    """

    def __init__(self, defaults=None, datasrc=None):
        self._datasrc = datasrc or {}
        self.load(defaults or {})

    def __getitem__(self, key):
        """Override to take `self._datasrc` into account."""
        if key in self:
            return dict.__getitem__(self, key)
        else:
            return object.__getattribute__(self, '_datasrc')[key]

    def __setitem__(self, key, value):
        """Override to only allow uppercase `key` to be set."""
        if key.isupper():
            dict.__setitem__(self, key, value)
        else:
            raise ValueError('Only uppercase keys are allowed')

    def __getattr__(self, name):
        """Override to treat the string `name` first as a dict key,
        then as an attribute name if `KeyError` is raised.
        """
        try:
            return self.__getitem__(name)
        except KeyError:
            return object.__getattribute__(self, name)

    def __setattr__(self, name, value):
        """Override to treat the pair `(name, value)` first as a dict item,
        then as an attribute if `ValueError` is raised.
        """
        try:
            self.__setitem__(name, value)
        except ValueError:
            object.__setattr__(self, name, value)

    def from_mapping(self, mapping):
        """Update the values from the given mapping."""
        for key, value in iteritems(mapping):
            if key.isupper():
                self[key] = value

    def from_object(self, obj):
        """Update the values from the given object.

        Objects are usually either modules or classes.
        """
        for key in dir(obj):
            if key.isupper():
                self[key] = getattr(obj, key)

    def load(self, obj):
        """Update the values from either a mapping or an object."""
        if isinstance(obj, dict):
            self.from_mapping(obj)
        else:
            self.from_object(obj)
