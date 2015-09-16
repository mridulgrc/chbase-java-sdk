import os
_package_name = "package com.chbase.thing.oxm.jaxb.cda;\n"
for _class in os.listdir('.'):
    if '.java' not in _class:
        continue
    print _class
    contents = ""
    with open(_class) as f:
        for line in f:
            if "package org.hl7.v3;" in line:
                contents += _package_name
            else:
                contents += line
    with open(_class, "w") as f:
        f.write(contents)
