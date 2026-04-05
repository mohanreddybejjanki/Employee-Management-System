// ============================================================
//  EMP Management System — Global JS
// ============================================================

document.addEventListener('DOMContentLoaded', function () {

    // ---- Sidebar toggle (desktop collapse) ----
    const sidebar  = document.querySelector('.sidebar');
    const mainContent = document.querySelector('.main-content');
    const toggleBtn   = document.querySelector('.sidebar-toggle');

    if (toggleBtn && sidebar) {
        toggleBtn.addEventListener('click', function () {
            sidebar.classList.toggle('mini');
            document.body.classList.toggle('sidebar-mini');
        });
    }

    // ---- Mobile hamburger ----
    const hamburger = document.querySelector('.hamburger');
    const overlay   = document.querySelector('.sidebar-overlay');

    if (hamburger && sidebar) {
        hamburger.addEventListener('click', function () {
            sidebar.classList.toggle('mobile-open');
            if (overlay) overlay.classList.toggle('show');
        });
    }

    if (overlay) {
        overlay.addEventListener('click', function () {
            sidebar.classList.remove('mobile-open');
            overlay.classList.remove('show');
        });
    }

    // ---- Auto-dismiss alerts after 4s ----
    document.querySelectorAll('.alert').forEach(function (el) {
        setTimeout(function () {
            el.style.transition = 'opacity .5s ease, max-height .5s ease';
            el.style.opacity = '0';
            el.style.maxHeight = '0';
            el.style.overflow = 'hidden';
            el.style.padding = '0';
            el.style.margin = '0';
        }, 4000);
    });

    // ---- Confirm delete ----
    document.querySelectorAll('[data-confirm]').forEach(function (el) {
        el.addEventListener('click', function (e) {
            if (!confirm(this.dataset.confirm || 'Are you sure?')) {
                e.preventDefault();
            }
        });
    });

    // ---- Table row click to view ----
    document.querySelectorAll('tr[data-href]').forEach(function (row) {
        row.style.cursor = 'pointer';
        row.addEventListener('click', function () {
            window.location.href = this.dataset.href;
        });
    });

    // ---- Active nav highlight ----
    const currentPath = window.location.pathname;
    document.querySelectorAll('.sidebar nav a').forEach(function (link) {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });

});