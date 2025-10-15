package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SPELL_R_LavaSurfing extends Card {

    public SPELL_R_LavaSurfing()
    {
        setImageSets("WX24-P1-064");

        setOriginalName("溶岩の波乗");
        setAltNames("ヨウガンノナミノリ Yougan no Naminori");
        setDescription("jp",
                "あなたの＜宝石＞のシグニ１体を対象とし、ターン終了時まで、それは@>@C：あなたの手札が２枚以下であるかぎり、このシグニは【アサシン】を得る。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Lava Surfing");
        setDescription("en",
                "Target 1 of your <<Gem>> SIGNI, and until end of turn, it gains:" +
                "@>@C: As long as there are 2 or less cards in your hand, this SIGNI gains [[Assassin]].@@" +
                "~#Target 1 of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "溶岩的波乘");
        setDescription("zh_simplified", 
                "你的<<宝石>>精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :你的手牌在2张以下时，这只精灵得到[[暗杀]]。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1) + Cost.colorless(1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.GEM)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onConstEffModGetSample));
                attachedConst.setCondition(this::onAttachedConstEffCond);
                attachAbility(spell.getTarget(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getHandCount(getOwner()) <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityAssassin());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
