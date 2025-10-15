package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.ExceedCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;

public final class KEY_G_MamaMODELOVE extends Card {

    public KEY_G_MamaMODELOVE()
    {
        setImageSets("WXK01-028");

        setOriginalName("ママ♥ＭＯＤＥ　ＬＯＶＥ");
        setAltNames("ママモードラブ Mama Moodo Rabu Mama Mode");
        setDescription("jp",
                "@C：あなたのセンタールリグは以下の能力を得る。" +
                "@>@A $T1 @[エクシード２]@：[[エナチャージ２]]@@" +
                "@E %G %X：あなたのデッキの一番上のカードをライフクロスに加える。\n" +
                "@A -A @[このキーを場からルリグトラッシュに置く]@：このターン、次に対戦相手のシグニがアタックしたとき、そのアタックを無効にする。"
        );

        setName("en", "Mama♥MODE LOVE");
        setDescription("en",
                "@C: Your center LRIG gains:" +
                "@>@A $T1 @[Exceed 2]@: [[Ener Charge 2]]@@" +
                "@E %G %X: Add the top card of your deck to life cloth.\n" +
                "@A -A @[Put this key from the field into the LRIG trash]@: This turn, the next time your opponent's SIGNI attacks, disable that attack."
        );

		setName("zh_simplified", "妈妈♥MODE LOVE");
        setDescription("zh_simplified", 
                "@C :你的核心分身得到以下的能力。\n" +
                "@>@A $T1 @[超越 2]@（从你的分身的下面把牌合计2张放置到分身废弃区）[[能量填充2]]@@@@\n" +
                "@E %G%X:你的牌组最上面的牌加入生命护甲。\n" +
                "@A 攻击阶段这张钥匙牌从场上放置到分身废弃区:这个回合，当下一次对战对手的精灵攻击时，那次攻击无效。\n"
        );

        setType(CardType.KEY);
        setColor(CardColor.GREEN);
        setCost(Cost.coin(1));

        setPlayFormat(PlayFormat.KEY);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().own().LRIG(), new AbilityGainModifier(this::onConstEffSharedModGetSample));

            registerEnterAbility(new EnerCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1)), this::onEnterEff);

            ActionAbility act = registerActionAbility(new TrashCost(), this::onActionEff);
            act.setActiveUseTiming(UseTiming.ATTACK);
        }

        private Ability onConstEffSharedModGetSample(CardIndex cardIndex)
        {
            ActionAbility attachedAct = cardIndex.getIndexedInstance().registerActionAbility(new ExceedCost(2), this::onAttachedActionEff);
            attachedAct.setUseLimit(UseLimit.TURN, 1);
            return attachedAct;
        }
        private void onAttachedActionEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().enerCharge(2);
        }

        private void onEnterEff()
        {
            addToLifeCloth(1);
        }

        private void onActionEff()
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd());
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_ATTACKED, getOwner(), record, data -> {
                if(isOwnCard(data.getSourceCardIndex()) || !CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType())) return RuleCheckState.IGNORE;
                
                record.forceExpire();
                
                return RuleCheckState.BLOCK;
            });
        }
    }
}
