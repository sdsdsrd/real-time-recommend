# -*- coding: utf-8 -*-

import os
import sys
from importlib import import_module

from six import reraise


def import_string(import_path):
    """Import a module path and return the module/attribute designated by
    the last name in the path. Raise ImportError if the import failed.
    """
    # The destination object is a module
    try:
        module = import_module(import_path)
    except ImportError:
        if '.' not in import_path:
            raise
    else:
        return module

    # The destination object is an attribute
    module_path, attr_name = import_path.rsplit('.', 1)
    module = import_module(module_path)
    try:
        return getattr(module, attr_name)
    except AttributeError:
        msg = (
            'No module named "{0}.{1}", nor does a module '
            'named "{0}" define a "{1}" attribute'.format(
                module_path, attr_name
            )
        )
        reraise(ImportError, ImportError(msg), sys.exc_info()[2])


def get_envvar(name, silent=False):
    """Get the value of the given environment variable."""
    value = os.environ.get(name)
    if value is None:
        if not silent:
            raise RuntimeError(
                'The environment variable %r is not set '
                'and as such configuration could not be '
                'loaded. Set this variable and make it '
                'point to a configuration file' % name
            )
        else:
            return ''
    return value
