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
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class SPELL_G_SpearOfStruggle extends Card {

    public SPELL_G_SpearOfStruggle()
    {
        setImageSets("WXDi-P15-071");

        setOriginalName("闘槍");
        setAltNames("トウソウ Tousou");
        setDescription("jp",
                "@[ベット]@ ― #C #C #C\n\n" +
                "あなたの＜闘争派＞のシグニ１体を対象とし、ターン終了時まで、それは@>@C：このシグニは正面のシグニのパワーが8000以下であるかぎり、【ランサー】を得る。@@を得る。あなたがベットしていた場合、代わりにそれは@>@C：【Ｓランサー】@@を得る。" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Battlespear");
        setDescription("en",
                "Bet -- #C #C #C\n\nTarget <<War Division>> SIGNI on your field gains@>@C: As long as the SIGNI in front of this SIGNI has power 8000 or less, this SIGNI gains [[Lancer]].@@until end of turn. If you made a bet, it gains@>@C: [[S Lancer]].@@until end of turn instead." +
                "~#[[Ener Charge 1]]. Then, add up to one target SIGNI from your Ener Zone to your hand or put it onto your field."
        );
        
        setName("en_fan", "Spear of Struggle");
        setDescription("en_fan",
                "@[Bet]@ - #C #C #C\n\n" +
                "Target 1 of your <<Struggle Faction>> SIGNI, and until end of turn, it gains:" +
                "@>@C: As long as the SIGNI in front of this SIGNI has power 8000 or less, this SIGNI gains [[Lancer]].@@" +
                "If you bet, until end of turn, it gains [[S Lancer]] instead." +
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "斗枪");
        setDescription("zh_simplified", 
                "下注—#C #C #C\n" +
                "你的<<闘争派>>精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :这只精灵的正面的精灵的力量在8000以下时，得到[[枪兵]]。@@\n" +
                "。你下注的场合，作为替代，其得到\n" +
                "@>@C :[[S枪兵]]@@" +
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));

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
            spell.setBetCost(new CoinCost(3));

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withClass(CardSIGNIClass.STRUGGLE_FACTION)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                if(!spell.hasUsedBet())
                {
                    ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                    attachedConst.setCondition(this::onAttachedConstEffCond);

                    attachAbility(spell.getTarget(), attachedConst, ChronoDuration.turnEnd());
                } else {
                    attachAbility(spell.getTarget(), new StockAbilitySLancer(), ChronoDuration.turnEnd());
                }
            }
        }
        private ConditionState onAttachedConstEffCond(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getOppositeSIGNI() != null &&
                   cardIndex.getIndexedInstance().getOppositeSIGNI().getIndexedInstance().getPower().getValue() <= 8000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityLancer());
        }

        private void onLifeBurstEff()
        {
            enerCharge(1);
            
            CardIndex target = playerTargetCard(0,1, new TargetFilter().own().SIGNI().fromEner()).get();
            if(target != null)
            {
                if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
                {
                    addToHand(target);
                } else {
                    putOnField(target);
                }
            }
        }
    }
}
