/**
 * Created by gs on 07.05.2017.
 */
var commonModule = (function () {
    return {
        showMusicDropdown: function () {
            $('#musicDropdown').slideDown();
        },
        hideMusicDropdown: function () {
            $('#musicDropdown').slideUp();
        },
        showMusicLoadModal: function () {
            $('#musicUploadModal').modal({
                backdrop: false,
                show: true
            });
        },
        hideMusicLoadModal: function () {
            $('#musicUploadModal').modal('hide');
        }
    }
})();