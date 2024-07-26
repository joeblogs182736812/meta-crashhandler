SUMMARY = "Tim Bird's experimental crash_handler program."
HOMEPAGE = "http://elinux.org/Crash_handler"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "https://github.com/joeblogs182736812/meta-crashhandler;branch=master \
           file://configure-crash-handler.service \
"
SRC_URI[md5sum] = "e7cae8678fa6155626f0dbafde054332"
SRC_URI[sha256sum] = "5bbef1a84982c899cafbb7be02fd3ba9174b917510d7f4175ba6f0901ed5540a"

inherit systemd

DEPENDS = "libunwind"
RDEPENDS_${PN} = " libunwind"

S = "${WORKDIR}/git"
SRCREV = "${AUTOREV}"
PV = "0.8.0+git${SRCREV}"

FILES_${PN} += "${sbindir}/crash_handler"

TARGET_CC_ARCH += "${LDFLAGS}"

do_compile() {
    oe_runmake CC="${CC}"
}

do_install() {
    install -d ${D}${sbindir}
    install -d ${D}${systemd_unitdir}/system

    install -m 0755 ${S}/crash_handler ${D}${sbindir}
    install -m 644 ${WORKDIR}/*.service      ${D}/${systemd_unitdir}/system
}

SYSTEMD_SERVICE_${PN} = "configure-crash-handler.service"
