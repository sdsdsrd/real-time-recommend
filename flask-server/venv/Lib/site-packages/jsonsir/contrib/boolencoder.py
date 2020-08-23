#!/usr/bin/env python
# -*- coding: utf-8 -*-

from jsonsir import Encoder


class BoolEncoder(Encoder):

    type_name = 'bool'
    type_value = bool

    def _decode(self, value):
        return value == 'true'
