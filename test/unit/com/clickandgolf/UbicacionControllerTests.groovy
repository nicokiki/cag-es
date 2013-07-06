package com.clickandgolf



import org.junit.*
import grails.test.mixin.*

@TestFor(UbicacionController)
@Mock(Ubicacion)
class UbicacionControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/ubicacion/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.ubicacionInstanceList.size() == 0
        assert model.ubicacionInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.ubicacionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.ubicacionInstance != null
        assert view == '/ubicacion/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/ubicacion/show/1'
        assert controller.flash.message != null
        assert Ubicacion.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/ubicacion/list'


        populateValidParams(params)
        def ubicacion = new Ubicacion(params)

        assert ubicacion.save() != null

        params.id = ubicacion.id

        def model = controller.show()

        assert model.ubicacionInstance == ubicacion
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/ubicacion/list'


        populateValidParams(params)
        def ubicacion = new Ubicacion(params)

        assert ubicacion.save() != null

        params.id = ubicacion.id

        def model = controller.edit()

        assert model.ubicacionInstance == ubicacion
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/ubicacion/list'

        response.reset()


        populateValidParams(params)
        def ubicacion = new Ubicacion(params)

        assert ubicacion.save() != null

        // test invalid parameters in update
        params.id = ubicacion.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/ubicacion/edit"
        assert model.ubicacionInstance != null

        ubicacion.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/ubicacion/show/$ubicacion.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        ubicacion.clearErrors()

        populateValidParams(params)
        params.id = ubicacion.id
        params.version = -1
        controller.update()

        assert view == "/ubicacion/edit"
        assert model.ubicacionInstance != null
        assert model.ubicacionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/ubicacion/list'

        response.reset()

        populateValidParams(params)
        def ubicacion = new Ubicacion(params)

        assert ubicacion.save() != null
        assert Ubicacion.count() == 1

        params.id = ubicacion.id

        controller.delete()

        assert Ubicacion.count() == 0
        assert Ubicacion.get(ubicacion.id) == null
        assert response.redirectedUrl == '/ubicacion/list'
    }
}
