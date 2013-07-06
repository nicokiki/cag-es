package com.clickandgolf



import org.junit.*
import grails.test.mixin.*

@TestFor(PromocionController)
@Mock(Promocion)
class PromocionControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/promocion/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.promocionInstanceList.size() == 0
        assert model.promocionInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.promocionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.promocionInstance != null
        assert view == '/promocion/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/promocion/show/1'
        assert controller.flash.message != null
        assert Promocion.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/promocion/list'


        populateValidParams(params)
        def promocion = new Promocion(params)

        assert promocion.save() != null

        params.id = promocion.id

        def model = controller.show()

        assert model.promocionInstance == promocion
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/promocion/list'


        populateValidParams(params)
        def promocion = new Promocion(params)

        assert promocion.save() != null

        params.id = promocion.id

        def model = controller.edit()

        assert model.promocionInstance == promocion
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/promocion/list'

        response.reset()


        populateValidParams(params)
        def promocion = new Promocion(params)

        assert promocion.save() != null

        // test invalid parameters in update
        params.id = promocion.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/promocion/edit"
        assert model.promocionInstance != null

        promocion.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/promocion/show/$promocion.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        promocion.clearErrors()

        populateValidParams(params)
        params.id = promocion.id
        params.version = -1
        controller.update()

        assert view == "/promocion/edit"
        assert model.promocionInstance != null
        assert model.promocionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/promocion/list'

        response.reset()

        populateValidParams(params)
        def promocion = new Promocion(params)

        assert promocion.save() != null
        assert Promocion.count() == 1

        params.id = promocion.id

        controller.delete()

        assert Promocion.count() == 0
        assert Promocion.get(promocion.id) == null
        assert response.redirectedUrl == '/promocion/list'
    }
}
