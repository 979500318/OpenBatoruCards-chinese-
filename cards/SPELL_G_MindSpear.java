package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SPELL_G_MindSpear extends Card {

    public SPELL_G_MindSpear()
    {
        setImageSets("WXDi-P13-082");

        setOriginalName("心槍");
        setAltNames("シンソウ Shinsou");
        setDescription("jp",
                "あなたの#Sのシグニ１体を対象とし、ターン終了時まで、それは@>@C：このシグニのパワーが正面のシグニと同じであるかぎり、このシグニは【ランサー】を得る。@@を得る。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Soulspear");
        setDescription("en",
                "Target #S SIGNI on your field gains@>@C: As long as this SIGNI has the same power as the SIGNI in front of it, it gains [[Lancer]].@@until end of turn." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Strong Spear");
        setDescription("en_fan",
                "Target 1 of your #S @[Dissona]@ SIGNI, and until end of turn, it gains:" +
                "@>@C: As long as the SIGNI in front of this SIGNI has the same power as this SIGNI, this SIGNI gains [[Lancer]].@@" +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "心枪");
        setDescription("zh_simplified", 
                "你的#S的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :这只精灵的力量与正面的精灵相同时，这只精灵得到[[枪兵]]。@@" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1) + Cost.colorless(1));

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().dissona().SIGNI()));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                attachedConst.setCondition(this::onAttachedConstEffCond);
                
                attachAbility(spell.getTarget(), attachedConst, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedConstEffCond(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getOppositeSIGNI() != null &&
                   cardIndex.getIndexedInstance().getOppositeSIGNI().getIndexedInstance().getPower().getValue().equals(getPower().getValue()) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
