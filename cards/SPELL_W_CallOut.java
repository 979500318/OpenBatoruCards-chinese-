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
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.ExceedCost;

public final class SPELL_W_CallOut extends Card {

    public SPELL_W_CallOut()
    {
        setImageSets("WXDi-P10-050");

        setOriginalName("コール・アウト");
        setAltNames("コールアウト Kooru Auto");
        setDescription("jp",
                "このスペルを使用する際、使用コストとして追加でエクシード７を支払ってもよい。\n\n" +
                "対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。追加でエクシード７を支払っていた場合、代わりにカードを１枚引き、ターン終了時まで、対戦相手のすべてのシグニは能力を失う。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Call Out");
        setDescription("en",
                "As you use this spell, you may pay Exceed 7 as an additional use cost.\n\n" +
                "Target SIGNI on your opponent's field loses its abilities until end of turn. If you paid the Exceed 7, instead draw a card and all SIGNI on your opponent's field lose their abilities until end of turn." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Call Out");
        setDescription("en_fan",
                "While using this spell, you may pay an additional @[Exceed 7]@ for its use cost.\n\n" +
                "Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities. If you paid an additional @[Exceed 7]@, instead draw 1 card, and until end of turn, all of your opponent's SIGNI lose their abilities." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );

		setName("zh_simplified", "呼唤·放逐");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以作为使用费用追加把超越7支付。（从你的分身的下面把牌合计7张放置到分身废弃区）\n" +
                "对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。追加把超越7支付过的场合，作为替代，抽1张牌，直到回合结束时为止，对战对手的全部的精灵的能力失去。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.WHITE);

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
            spell.setAdditionalCost(new ExceedCost(7));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            if(!spell.hasPaidAdditionalCost()) spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()));
        }
        private void onSpellEff()
        {
            if(!spell.hasPaidAdditionalCost())
            {
                disableAllAbilities(spell.getTarget(), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            } else {
                draw(1);

                disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
