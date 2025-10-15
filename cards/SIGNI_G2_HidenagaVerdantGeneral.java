package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataSIGNIClass;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_G2_HidenagaVerdantGeneral extends Card {

    public SIGNI_G2_HidenagaVerdantGeneral()
    {
        setImageSets("WX25-P1-092");

        setOriginalName("翠将　ヒデナガ");
        setAltNames("スイショウヒデナガ Suishou Hidenaga");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるすべてのシグニがそれぞれ共通するクラスを持たない場合、対戦相手のレベル２以下のシグニ１体を対象とし、そのレベル１につき%Gを支払ってもよい。そうした場合、それをバニッシュする。"
        );

        setName("en", "Hidenaga, Verdant General");
        setDescription("en",
                "@U: At the beginning of your attack phase, if all of your SIGNI do not share a common class, target 1 of your opponent's level 2 or lower SIGNI, and you may pay %G for each of its levels. If you do, banish it."
        );

		setName("zh_simplified", "翠将 丰臣秀长");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的全部的精灵不持有共通类别的场合，对战对手的等级2以下的精灵1只作为对象，可以依据其的等级的数量，每有1级就支付%G。这样做的场合，将其破坏。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = new TargetFilter().own().SIGNI().getExportedData();

            if(!CardDataSIGNIClass.shareCommonClass(data))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
                
                if(target != null && payEner(Cost.color(CardColor.GREEN, target.getIndexedInstance().getLevel().getValue())))
                {
                    banish(target);
                }
            }
        }
    }
}
