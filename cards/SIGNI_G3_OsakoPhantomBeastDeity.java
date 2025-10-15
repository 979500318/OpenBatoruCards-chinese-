package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G3_OsakoPhantomBeastDeity extends Card {

    public SIGNI_G3_OsakoPhantomBeastDeity()
    {
        setImageSets("WX24-P1-046");
        setLinkedImageSets("WX24-P1-014");

        setOriginalName("幻獣神　オサコ");
        setAltNames("ゲンジュウシンオサコ Genjuushin Osako");
        setDescription("jp",
                "@U $T1：あなたの他の＜地獣＞のシグニ１体が場に出たとき、【エナチャージ１】をする。\n" +
                "@U：あなたのアタックフェイズ開始時、あなたの場に《讃型　緑姫》がいる場合、あなたのトラッシュから＜地獣＞のシグニ１枚を対象とし、それをデッキの一番下に置いてもよい。その後、あなたの＜地獣＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーをこの方法でデッキに移動したシグニのパワーと同じだけ＋（プラス）する。"
        );

        setName("en", "Osako, Phantom Beast Deity");
        setDescription("en",
                "@U $T1: When 1 of your other <<Earth Beast>> SIGNI enters the field, [[Ener Charge 1]].\n" +
                "@U: At the beginning of your attack phase, if your LRIG is \"Midoriko, Type Appeal\", target 1 <<Earth Beast>> SIGNI from your trash, and you may put it on the bottom of your deck. Then, target 1 of your <<Earth Beast>> SIGNI, and until the end of your opponent's next turn, it gets + (plus) power equal to the power of the SIGNI put on the bottom of your deck this way."
        );

		setName("zh_simplified", "幻兽神 小御先");
        setDescription("zh_simplified", 
                "@U $T1 :当你的其他的<<地獣>>精灵1只出场时，[[能量填充1]]。\n" +
                "@U :你的攻击阶段开始时，你的场上有《讃型　緑姫》的场合，从你的废弃区把<<地獣>>精灵1张作为对象，可以将其放置到牌组最下面。然后，你的<<地獣>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+（加号）与这个方法往牌组移动的精灵的力量相同的数值。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
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

            AutoAbility auto1 = registerAutoAbility(GameEventId.ENTER, this::onAutoEff1);
            auto1.setCondition(this::onAutoEff1Cond);
            auto1.setUseLimit(UseLimit.TURN, 1);
            
            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private ConditionState onAutoEff1Cond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.EARTH_BEAST) && caller != getCardIndex() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            enerCharge(1);
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("讃型　緑姫"))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST).fromTrash()).get();
                
                if(target != null && playerChoiceActivate())
                {
                    double power = target.getIndexedInstance().getPower().getValue();
                    if(returnToDeck(target, DeckPosition.BOTTOM))
                    {
                        target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.EARTH_BEAST)).get();
                        if(target != null) gainPower(target, power, ChronoDuration.nextTurnEnd(getOpponent()));
                    }
                }
            }
        }
    }
}
