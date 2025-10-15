package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass.CardSIGNIClassValue;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SIGNI_G3_CodeLabyrinthTabiNoTobira extends Card {

    public SIGNI_G3_CodeLabyrinthTabiNoTobira()
    {
        setImageSets("WX25-P1-058", "WX25-P1-058U");
        setLinkedImageSets("WX25-P1-026");

        setOriginalName("コードラビリンス　タビノトビラ");
        setAltNames("コードラビリンスタビノトビラ Koodo Rabirinsu Tabi no Tobira");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、対戦相手のシグニ１体を対象とし、%Xを支払ってもよい。そうした場合、クラス１つを宣言する。ターン終了時まで、それはクラスを失い、宣言されたクラスを得る。\n" +
                "@U：このシグニがアタックしたとき、あなたの場に《紡ぎし冒険の扉　アト＝トレ》がいる場合、あなたの場にあるいずれかのシグニと共通するクラスを持つ対戦相手のシグニ１体を対象とし、%Gを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Code Labyrinth Tabi-no-Tobira");
        setDescription("en",
                "@U: At the beginning of your attack phase, target 1 of your opponent's SIGNI, and you may pay %X. If you do, declare a class. Until end of turn, the target SIGNI loses its classes and gains the declared class.\n" +
                "@U: Whenever this SIGNI attacks, if your LRIG is \"At-Tre, The Spun Door of Adventure\", target 1 of your opponent's SIGNI that shares a common class with a SIGNI on your field, and you may pay %G. If you do, banish it."
        );

		setName("zh_simplified", "迷牢代号 旅行之门");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，对战对手的精灵1只作为对象，可以支付%X。这样做的场合，类别1种宣言。直到回合结束时为止，其的类别失去，得到宣言的类别。\n" +
                "@U 当这只精灵攻击时，你的场上有《紡ぎし冒険の扉:アト＝トレ》的场合，持有与你的场上任一只的精灵共通类别的对战对手的精灵1只作为对象，可以支付%G。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter().OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(1)))
            {
                CardSIGNIClassValue chosenClass = new CardSIGNIClassValue(CardSIGNIClassGeneration.PERFORMER/**/, playerChoiceSIGNIClass());
                
                target.getIndexedInstance().getSIGNIClass().resetValue();
                setBaseValue(target, target.getIndexedInstance().getSIGNIClass(),List.of(chosenClass), ChronoDuration.turnEnd());
            }
        }
        
        private void onAutoEff2()
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("紡ぎし冒険の扉　アト＝トレ"))
            {
                Set<CardSIGNIClassValue> cacheClasses = new HashSet<>();
                forEachSIGNIOnField(getOwner(), cardIndex -> cacheClasses.addAll(cardIndex.getIndexedInstance().getSIGNIClass().getValue()));
                
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withClass(cacheClasses)).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, 1)))
                {
                    banish(target);
                }
            }
        }
    }
}
