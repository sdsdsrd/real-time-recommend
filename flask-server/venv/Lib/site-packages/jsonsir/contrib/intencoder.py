#!/usr/bin/env python
# -*- coding: utf-8 -*-

from jsonsir import Encoder


class IntEncoder(Encoder):

    type_name = 'int'
    type_value = int

    def _decode(self, value):
        return int(value)
