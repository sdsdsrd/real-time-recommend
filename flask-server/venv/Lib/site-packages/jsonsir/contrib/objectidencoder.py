#!/usr/bin/env python
# -*- coding: utf-8 -*-

from bson import ObjectId
from jsonsir import Encoder


class ObjectIdEncoder(Encoder):

    type_name = 'objectid'
    type_value = ObjectId

    def _encode(self, value):
        return str(value)

    def _decode(self, value):
        return ObjectId(value)
