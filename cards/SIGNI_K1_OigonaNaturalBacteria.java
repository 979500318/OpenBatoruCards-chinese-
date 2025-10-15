package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.DiscardCost;

public final class SIGNI_K1_OigonaNaturalBacteria extends Card {

    public SIGNI_K1_OigonaNaturalBacteria()
    {
        setImageSets("WXDi-P09-078", "SPDi01-102");
        setLinkedImageSets("WXDi-P09-046");

        setOriginalName("羅菌　オイゴナ");
        setAltNames("ラキンオイゴナ Rakin Oigona");
        setDescription("jp",
                "@U：あなたの《羅菌姫　ナナシ//メモリア》１体が場に出たとき、%Xを支払ってもよい。そうした場合、場にあるこのシグニをそのシグニの下に置く。\n" +
                "@E @[手札から黒のカード１枚を捨てる]@：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－3000する。"
        );

        setName("en", "Oigona, Natural Bacteria");
        setDescription("en",
                "@U: Whenever a \"Nanashi//Memoria, Bacteria Queen\" enters your field, you may pay %X. If you do, put this SIGNI on the field under that SIGNI.\n" +
                "@E @[Discard a black card]@: Target SIGNI on your opponent's field gets --3000 power until end of turn."
        );
        
        setName("en_fan", "Oigona, Natural Bacteria");
        setDescription("en_fan",
                "@U: Whenever 1 of your \"Nanashi//Memoria, Natural Bacteria Princess\" enters the field, you may pay %X. If you do, put this SIGNI from your field under that SIGNI.\n" +
                "@E @[Discard 1 black card from your hand]@: Target 1 of your opponent's SIGNI, and until end of turn, it gets --3000 power."
        );

		setName("zh_simplified", "罗菌 大肠杆菌");
        setDescription("zh_simplified", 
                "@U :当你的《羅菌姫　ナナシ//メモリア》1只出场时，可以支付%X。这样做的场合，场上的这只精灵放置到那只精灵的下面。\n" +
                "@E 从手牌把黑色的牌1张舍弃:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.ENTER, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new DiscardCost(new TargetFilter().withColor(CardColor.BLACK)), this::onEnterEff);
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getName().getValue().contains("羅菌姫　ナナシ//メモリア") ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && payEner(Cost.colorless(1)))
            {
                attach(caller, getCardIndex(), CardUnderType.UNDER_GENERIC);
            }
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -3000, ChronoDuration.turnEnd());
        }
    }
}
