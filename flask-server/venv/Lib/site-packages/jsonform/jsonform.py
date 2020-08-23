#!/usr/bin/env python
# -*- coding: utf-8 -*-

from jsonschema import Draft4Validator, validators
from jsonschema.exceptions import ValidationError


def extends(validator_class, partial):
    """Extend `Draft4Validator` with some validators."""

    def custom(validator, _custom, instance, schema):
        """Support user customized validators."""
        message = _custom(instance)
        if message is not None:
            yield ValidationError(message)

    def required(validator, _required, instance, schema):
        """Disable required validating if in partial mode.

        In normal (non-partial) mode, validate and yield errors with
        additional `path` and `schema_path` for each required field.
        """
        if partial:
            return

        if not validator.is_type(instance, "object"):
            return
        for property in _required:
            if property not in instance:
                yield ValidationError(
                    '%r is a required property' % property,
                    path=(property,), schema_path=(property,)
                )

    return validators.extend(
        validator_class, {'custom': custom, 'required': required},
    )


class JsonForm(object):
    def __init__(self, document, partial=False):
        # check `schema` is defined and valid
        if not hasattr(self, 'schema'):
            raise AttributeError('form `schema` missing')
        ExtendedDraft4Validator = extends(Draft4Validator, partial)
        ExtendedDraft4Validator.check_schema(self.schema)

        self.validator = ExtendedDraft4Validator(self.schema)
        self.document = document
        self._errors = None

    def is_valid(self):
        return not self.errors

    @property
    def errors(self):
        """Get errors from jsonschema validation with human friendly format.

        Also replace origin error messages with customized ones if
        `messages` is defined in the form class.
        """
        if self._errors is None:
            messages = {}
            if hasattr(self, 'messages'):
                messages = self.messages
                assert isinstance(messages, dict), \
                       'form `messages` must be a dict'

            self._errors = {}
            for error in self.validator.iter_errors(self.document):
                data_path = '.'.join([
                    str(path) for path in error.absolute_path
                ])
                schema_path = '.'.join([
                    str(path) for path in error.absolute_schema_path
                ])
                self._errors[data_path] = (messages.get(schema_path) or
                                           error.message)

        return self._errors
