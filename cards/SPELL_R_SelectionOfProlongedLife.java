package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_R_SelectionOfProlongedLife extends Card {

    public SPELL_R_SelectionOfProlongedLife()
    {
        setImageSets("WX24-P4-068");

        setOriginalName("延命の選定");
        setAltNames("エンメイノセンテイ Enmei no Sentei");
        setDescription("jp",
                "対戦相手のパワー8000以下のシグニ１体を対象とし、それをバニッシュする。あなたの場にレベル４以上のルリグがいる場合、代わりに対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Selection of Prolonged Life");
        setDescription("en",
                "Target 1 of your opponent's SIGNI with power 8000 or less, and banish it. If there is a level 4 or higher LRIG on your field, instead target 1 of your opponent's SIGNI with power 10000 or less, and banish it." +
                "~#Target of your opponent's upped SIGNI, and banish it."
        );

		setName("zh_simplified", "延命的选定");
        setDescription("zh_simplified", 
                "对战对手的力量8000以下的精灵1只作为对象，将其破坏。你的场上有等级4以上的分身的场合，作为替代，对战对手的力量10000以下的精灵1只作为对象，将其破坏。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 1));

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0, getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() < 4 ? 8000 : 10000)));
        }
        private void onSpellEff()
        {
            banish(spell.getTarget());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
            banish(target);
        }
    }
}
