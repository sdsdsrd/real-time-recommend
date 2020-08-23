#!/usr/bin/env python
# -*- coding: utf-8 -*-

import unittest

from jsonform import JsonForm


class PersonForm(JsonForm):

    def validate_name(value):
        if value != 'me':
            return 'not ok, yeah yeah'

    schema = {
        'type': 'object',
        'properties': {
            'name': {'custom': validate_name},
            'age': {'type': 'integer'},
            'address': {
                'type': 'object',
                'properties': {
                    'country': {'type': 'string'},
                    'city': {
                        'items': {
                            'anyOf': [
                                {'type': 'string', 'maxLength': 2},
                                {'type': 'integer', 'minimum': 5}
                            ]
                        }
                    }
                }
            }
        },
        'required': ['name']
    }

    messages = {
        'required.name': u'请填写姓名。',
        'properties.age.type': u'你这是年龄吗？',
        'properties.address.properties.city.items.anyOf': u'输入有误，亲！'
    }


class TestPersonForm(unittest.TestCase):

    def test_name(self):
        document = {
            'name': 'you',
        }
        form = PersonForm(document)
        if not form.is_valid():
            self.assertEqual(form.errors['name'], 'not ok, yeah yeah')

    def test_city(self):
        document = {
            'name': 'me',
            'address': {
                'city': [10, {}]
            }
        }
        form = PersonForm(document)
        if not form.is_valid():
            self.assertEqual(form.errors['address.city.1'],
                             u'输入有误，亲！')

    def test_required(self):
        document = {}
        form = PersonForm(document)
        if not form.is_valid():
            self.assertEqual(form.errors['name'], u'请填写姓名。')

    def test_partial(self):
        document = {
            'age': 'xx',
        }
        form = PersonForm(document, partial=True)
        if not form.is_valid():
            self.assertTrue('name' not in form.errors)
            self.assertEqual(form.errors['age'], u'你这是年龄吗？')


if __name__ == '__main__':
    unittest.main()
