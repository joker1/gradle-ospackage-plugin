#!/bin/sh
set -e
<% commands.each {command -> %>
<%= command %>
<% } %>
