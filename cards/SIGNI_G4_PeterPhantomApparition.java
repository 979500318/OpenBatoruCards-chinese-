package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G4_PeterPhantomApparition extends Card {

    public SIGNI_G4_PeterPhantomApparition()
    {
        setImageSets("WDK03-011");

        setOriginalName("幻怪　ピーター");
        setAltNames("ゲンカイピーターGenkai Piitaa");
        setDescription("jp",
                "@U $T1：あなたがアーツを使用したとき、ターン終了時まで、あなたのすべての＜怪異＞のシグニは【ランサー】を得る。" +
                "~#対戦相手のパワー10000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Peter, Phantom Apparition");
        setDescription("en",
                "@U $T1: When you use an ARTS, until end of turn, all of your <<Apparition>> SIGNI gain [[Lancer]]." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or more, and banish it."
        );

		setName("zh_simplified", "幻怪 彼得潘");
        setDescription("zh_simplified", 
                "@U $T1 :当你把必杀使用时，直到回合结束时为止，你的全部的<<怪異>>精灵得到[[枪兵]]。" +
                "~#对战对手的力量10000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setLRIGType(CardLRIGType.MIDORIKO);
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClass.APPARITION);
        setLevel(4);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY);
    }


    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.USE_ARTS, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            forEachSIGNIOnField(getOwner(), cardIndex -> {
                if(cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.APPARITION))
                {
                    attachAbility(cardIndex, new StockAbilityLancer(), ChronoDuration.turnEnd());
                }
            });
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(10000,0)).get();
            banish(target);
        }
    }
}
