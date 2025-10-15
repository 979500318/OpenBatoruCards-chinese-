package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_G2_RememberTHEDOORNaturalStar extends Card {

    public SIGNI_G2_RememberTHEDOORNaturalStar()
    {
        setImageSets("WXDi-P16-079");

        setOriginalName("羅星　リメンバ//THE DOOR");
        setAltNames("ラセイリメンバザドアー Rasei Rimenba Za Doaa");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの他の＜闘争派＞のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋3000する。\n" +
                "@A $T1 #C #C：あなたのエナゾーンからシグニ１枚を対象とし、それを手札に加える。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Remember//THE DOOR, Natural Planet");
        setDescription("en",
                "@U: At the beginning of your attack phase, another target <<War Division>> SIGNI on your field gets +3000 power until the end of your opponent's next end phase.\n@A $T1 #C #C: Add target SIGNI from your Ener Zone to your hand." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a LRIG this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Remember//THE DOOR, Natural Star");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 1 of your other <<Struggle Faction>> SIGNI, and until the end of your opponent's next turn, it gets +3000 power.\n" +
                "@A $T1 #C #C: Target 1 SIGNI from your ener zone, and add it to your hand." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "罗星 忆//THE DOOR");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的其他的<<闘争派>>精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+3000。\n" +
                "@A $T1 #C #C:从你的能量区把精灵1张作为对象，将其加入手牌。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.STRUGGLE_FACTION,CardSIGNIClass.SPACE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            
            ActionAbility act = registerActionAbility(new CoinCost(2), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION).except(getCardIndex())).get();
            if(target != null) gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
