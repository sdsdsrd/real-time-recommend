# -*- coding: utf-8 -*-

import warnings
warnings.simplefilter('default', DeprecationWarning)

from .utils import import_string, get_envvar


def str_object(obj_path, silent=False, is_envvar=False):
    """Get a configuration object from a string."""
    if is_envvar:
        obj_path = get_envvar(obj_path, silent=silent)

    try:
        obj = import_string(obj_path)
    except (ImportError, ValueError):
        if not silent:
            raise
        else:
            obj = None

    return obj


def envvar_object(var_name, silent=False):
    """Get a configuration object from an environment variable."""
    warnings.warn('`envvar_object(name, silent)` is deprecated, use '
                  '`str_object(name, silent, is_envvar=True)` instead.',
                  DeprecationWarning)
    return str_object(var_name, silent=silent, is_envvar=True)
