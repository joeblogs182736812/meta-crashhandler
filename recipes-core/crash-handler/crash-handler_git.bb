SUMMARY = "Tim Bird's experimental crash_handler program."
HOMEPAGE = "http://elinux.org/Crash_handler"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "https://github.com/joeblogs182736812/meta-crashhandler;branch=master \
           file://configure-crash-handler.service \
"
SRC_URI[md5sum] = "8e322b3ac20f8c7fdd739c62e4da074f"
SRC_URI[sha256sum] = "dcef18ffc773b1fbb415cdb5d116c7731e18ca71c8a6137e7fa6ba1ce30413ad"

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
