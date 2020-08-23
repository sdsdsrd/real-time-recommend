#!/usr/bin/env python
# -*- coding: utf-8 -*-

import re

from jsonsir import Encoder


class RegexEncoder(Encoder):

    type_name = 'regex'
    type_value = re._pattern_type

    def _encode(self, value):
        return '/%s/' % value.pattern

    def _decode(self, value):
        return re.compile(value[1:-1])
