package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
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

public final class SPELL_G_Echo extends Card {

    public SPELL_G_Echo()
    {
        setImageSets("WXDi-P14-065", "SPDi37-04");
        setLinkedImageSets("WXDi-P14-047");

        setOriginalName("反響");
        setAltNames("ハンキョウ Hankyou");
        setDescription("jp",
                "あなたの緑のシグニ１体を対象とし、ターン終了時まで、それは@>@C：このシグニは正面のシグニのパワーが8000以下であるかぎり、【ランサー】を得る。@@を得る。それが《コードオーダー　メル//フェゾーネ》の場合、それは覚醒する。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Reverb");
        setDescription("en",
                "Target green SIGNI on your field gains@>@C: As long as the SIGNI in front of this SIGNI has power 8000 or less, this SIGNI gains [[Lancer]].@@until end of turn. If it is \"Mel//Fesonne, Code: Order\", it is awakened." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Echo");
        setDescription("en_fan",
                "Target 1 of your green SIGNI, and until end of turn, it gains:" +
                "@>@C: As long as the SIGNI in front of this SIGNI has power 8000 or less, this SIGNI gains [[Lancer]].@@" +
                "If that SIGNI is \"Code Order Mel//Fessone\", it awakens." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "反响");
        setDescription("zh_simplified", 
                "你的绿色的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :这只精灵的正面的精灵的力量在8000以下时，得到[[枪兵]]。@@\n" +
                "。其是《コードオーダー　メル//フェゾーネ》的场合，将其觉醒。（精灵觉醒后在场上保持觉醒状态）" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
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

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withColor(CardColor.GREEN)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                attachedConst.setCondition(this::onAttachedConstEffCond);
                attachAbility(spell.getTarget(), attachedConst, ChronoDuration.turnEnd());
                
                if(spell.getTarget() != null && spell.getTarget().getIndexedInstance().getName().getValue().contains("コードオーダー　メル//フェゾーネ"))
                {
                    spell.getTarget().getIndexedInstance().getCardStateFlags().addValue(CardStateFlag.AWAKENED);
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
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
