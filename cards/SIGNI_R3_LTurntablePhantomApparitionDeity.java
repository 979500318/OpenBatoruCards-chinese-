package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_R3_LTurntablePhantomApparitionDeity extends Card {

    public SIGNI_R3_LTurntablePhantomApparitionDeity()
    {
        setImageSets("SPDi43-18");
        setLinkedImageSets("SPDi43-12");

        setOriginalName("幻獣神　Lターンテーブル");
        setAltNames("ゲンジュウシンエルターンテーブル Genjuushin Eru Taanteeburu");
        setDescription("jp",
                "@U $T1：あなたの《DJ.LOVIT 3rdVerse-ULT》がダウンしたとき、対戦相手のエナゾーンからカード１枚を対象とし、それをトラッシュに置く。それが対戦相手のセンタールリグと共通する色を持つ場合、対戦相手は【エナチャージ１】をしてもよい。\n" +
                "@U：このシグニがアタックしたとき、対戦相手のエナゾーンにあるカードが４枚以下の場合、あなたのルリグ１体を対象とし、%Rを支払ってもよい。そうした場合、それをアップする。"
        );

        setName("en", "L Turntable, Phantom Beast Deity");
        setDescription("en",
                "@U $T1: When your \"DJ.LOVIT 3rd Verse-ULT\" is downed, target 1 card from your opponent's ener zone, and put it into the trash. If it shares a common color with your opponent's center LRIG, your opponent may [[Ener Charge 1]].\n" +
                "@U: Whenever this SIGNI attacks, if there are 4 or less cards in your opponent's ener zone, target 1 of your LRIG, and you may pay %R. If you do, up it."
        );

		setName("zh_simplified", "幻兽神 L唱盘");
        setDescription("zh_simplified", 
                "@U $T1 :当你的《DJ.LOVIT3rdVerse-ULT》#D时，从对战对手的能量区把1张牌作为对象，将其放置到废弃区。其持有与对战对手的核心分身共通颜色的场合，对战对手可以[[能量填充1]]。\n" +
                "@U :当这只精灵攻击时，对战对手的能量区的牌在4张以下的场合，你的分身1只作为对象，可以支付%R。这样做的场合，将其竖直。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.DOWN, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getName().getValue().contains("DJ.LOVIT 3rdVerse-ULT") ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BURN).OP().fromEner()).get();
            
            if(target != null)
            {
                trash(target);
                
                if(target.getIndexedInstance().getColor().matches(getLRIG(getOpponent()).getIndexedInstance().getColor()) && playerChoiceActivate(getOpponent()))
                {
                    enerCharge(getOpponent(), 1);
                }
            }
        }

        private void onAutoEff2()
        {
            if(getEnerCount(getOpponent()) <= 4)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.UP).own().anyLRIG()).get();
                
                if(target != null && payEner(Cost.color(CardColor.RED, 1)))
                {
                    up(target);
                }
            }
        }
    }
}
