#!/usr/bin/env python
# -*- coding: utf-8 -*-

from datetime import datetime

from jsonsir import Encoder


class DateTimeEncoder(Encoder):

    type_name = 'datetime'
    type_value = datetime

    def __init__(self, date_format):
        self.date_format = date_format

    def _encode(self, value):
        return value.strftime(self.date_format)

    def _decode(self, value):
        return datetime.strptime(value, self.date_format)
