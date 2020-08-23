#!/usr/bin/env python
# -*- coding: utf-8 -*-

import re

from ._compat import str_type


pattern = re.compile(r'(\w+)\((.*)\)')


def cast(cast_value, data):
    """Convert values recursively in `data`."""

    def cast_core(value):
        if isinstance(value, dict):
            return cast_dict(value)
        elif isinstance(value, list):
            return cast_list(value)
        else:
            return cast_value(value)

    def cast_dict(value):
        for k, v in value.items():
            value[k] = cast_core(v)
        return value

    def cast_list(value):
        for i, v in enumerate(value):
            value[i] = cast_core(v)
        return value

    return cast_core(data)


class Serializer(object):

    def __init__(self, encoders=None):
        self.named_encoders = {}
        self.valued_encoders = {}
        if encoders:
            self.register(encoders)

    def register(self, encoders):
        for encoder in encoders:
            self.named_encoders[encoder.type_name] = encoder
            self.valued_encoders[encoder.type_value] = encoder

    def serialize(self, data, with_type_name=False):
        """Inplace serializing."""

        def cast_value(value):
            t = type(value)
            if t in self.valued_encoders:
                value = self.valued_encoders[t].encode(value, with_type_name)
            return value

        return cast(cast_value, data)

    def deserialize(self, data):
        """Inplace deserializing."""

        def cast_value(value):
            if isinstance(value, str_type):
                m = pattern.match(value)
                if m:
                    t, v = m.groups()
                    if t in self.named_encoders:
                        try:
                            value = self.named_encoders[t].decode(v)
                        except Exception:
                            pass
            return value

        return cast(cast_value, data)
