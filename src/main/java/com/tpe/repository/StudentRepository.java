package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // optional
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.grade=:pGrade") // JPQL ( neden kullandigimizi altta not ile belirttim)
        //@Query("SELECT s FROM Student s WHERE s.grade=:pGrade") // HQL
        //@Query(value = "SELECT * FROM Student s WHERE s.grade=:pGrade", nativeQuery = true) // native SQL
    List<Student> findAllEqualsGrade(@Param("pGrade") Integer grade);

    @Query("SELECT new com.tpe.dto.StudentDTO(s) FROM Student s WHERE s.id=:id") // StudentDTO st1 = new StudentDTO(Student);
    Optional<StudentDTO> findStudentDtoById(@Param("id") Long id);
}





    /*
    JPQL, JPA standardının bir parçasıdır ve JPA uygulayan herhangi bir ORM aracı tarafından desteklenir.
        HQL ise Hibernate’e özgüdür ve sadece Hibernate tarafından desteklenir.

    JPQL, JPA’nın desteklediği tüm özellikleri kapsar. HQL ise Hibernate’in desteklediği tüm özellikleri kapsar.
        Bu nedenle, HQL, JPQL’den daha fazla özelliğe sahiptir. Örneğin, HQL, alt sorguları FROM ifadesinde kullanabilir,
        JOIN FETCH ifadesini destekler, @Formula anotasyonunu kullanabilir ve kullanıcı tanımlı fonksiyonlar çağırabilir.

    JPQL, veritabanından bağımsızdır ve herhangi bir veritabanında çalışabilir.
        HQL ise veritabanına bağımlıdır ve bazı veritabanlarında çalışmayabilir. Örneğin, HQL, Oracle veritabanında LIMIT ifadesini kullanamaz
     */

