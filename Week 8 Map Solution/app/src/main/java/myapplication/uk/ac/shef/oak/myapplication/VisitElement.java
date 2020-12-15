/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */
package myapplication.uk.ac.shef.oak.myapplication;

public class VisitElement {
    int visit = -1;
    String name = "";

    public VisitElement(int visit) { this.visit = visit; }

    public VisitElement(String name) {
        this.name = name;
    }
}
