#!/usr/bin/env python
# -*- coding: utf-8 -*-

from ._compat import str_type


class Encoder(object):

    type_name = None
    type_value = None

    def encode(self, value, with_type_name=False):
        result = self._encode(value)
        # `with_type_name` flag can only affect string-type result
        if with_type_name and isinstance(result, str_type):
            result = '%s(%s)' % (self.type_name, result)
        return result

    def decode(self, value):
        return self._decode(value)

    def _encode(self, value):
        return value

    def _decode(self, value):
        return value
