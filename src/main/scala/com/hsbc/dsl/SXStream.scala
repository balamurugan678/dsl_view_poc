package com.hsbc.dsl

import com.thoughtworks.xstream.XStream

/**
  * Created by Bala on 19/04/2017.
  */
object SXStream {
  private val xstream = new XStream

  def fromXML[T](xml: String): T = {
    xstream.fromXML(xml).asInstanceOf[T]
  }

  def toXML[T](obj: T): String = {
    xstream.toXML(obj)
  }
}
