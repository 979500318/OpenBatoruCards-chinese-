package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.events.EventTarget;

public final class SPELL_R_AwakeningOfExcitement extends Card {

    public SPELL_R_AwakeningOfExcitement()
    {
        setImageSets("WXDi-P14-057");
        setLinkedImageSets("WXDi-P14-043");

        setOriginalName("熱気の開眼");
        setAltNames("ネッキノカイガン Nekki no Kaigan");
        setDescription("jp",
                "あなたの赤のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは@>@U $T1：このシグニが対戦相手の、能力か効果の対象になったとき、対戦相手は自分のエナゾーンからカード１枚を選びトラッシュに置く。@@を得る。それが《羅輝石　花代//フェゾーネ》の場合、それは覚醒する。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Awakening Fervor");
        setDescription("en",
                "Target red SIGNI on your field gains@>@U $T1: When this SIGNI becomes the target of an ability or effect of your opponent, your opponent chooses a card from their Ener Zone and puts it into their trash.@@until the end of your opponent's next end phase. If it is \"Hanayo//Fesonne, Natural Pyroxene\", it is awakened." +
                "~#Vanish target upped SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Awakening of Excitement");
        setDescription("en_fan",
                "Target 1 of your red SIGNI, and until the end of your opponent's next turn, it gains:" +
                "@>@U $T1: When this SIGNI is targeted by your opponent's ability or effect, your opponent chooses 1 card from their ener zone, and puts it into the trash.@@" +
                "If that SIGNI is \"Hanayo//Fessone, Natural Pyroxene\", it awakens." +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "热气的开眼");
        setDescription("zh_simplified", 
                "你的红色的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到\n" +
                "@>@U $T1 :当这只精灵被作为对战对手的，能力或效果的对象时，对战对手从自己的能量区选1张牌放置到废弃区。@@\n" +
                "。其是《羅輝石　花代//フェゾーネ》的场合，将其觉醒。（精灵觉醒后在场上保持觉醒状态）" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.RED)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.TARGET, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                attachedAuto.setUseLimit(UseLimit.TURN, 1);
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));

                if(spell.getTarget() != null && spell.getTarget().getIndexedInstance().getName().getValue().contains("羅輝石　花代//フェゾーネ"))
                {
                    spell.getTarget().getIndexedInstance().getCardStateFlags().addValue(CardStateFlag.AWAKENED);
                }
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && !isOwnCard(getEvent().getSourceCardIndex()) &&
                   CardLocation.isSIGNI(getAbility().getSourceCardIndex().getLocation()) &&
                   EventTarget.getDataSourceTargetRole() != getCurrentOwner() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndex = playerTargetCard(getOpponent(), new TargetFilter(TargetHint.BURN).own().fromEner()).get();
            getAbility().getSourceCardIndex().getIndexedInstance().trash(cardIndex);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
