package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_B_GATEDRIVE extends Card {

    public SPELL_B_GATEDRIVE()
    {
        setImageSets("WXDi-P15-083");

        setOriginalName("GATE DRIVE");
        setAltNames("ゲートドライブ Geeto Doraibu");
        setDescription("jp",
                "同じシグニゾーンに【ゲート】があるあなたのシグニ１体を対象とし、ターン終了時まで、それは@>@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、対戦相手が手札を３枚捨てないかぎり、ターン終了時まで、それのパワーを－8000する。@@を得る。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、手札を１枚捨ててもよい。そうした場合、それをデッキの一番下に置く。"
        );

        setName("en", "GATE DRIVE");
        setDescription("en",
                "Target SIGNI on your field in the same SIGNI Zone as a [[Gate]] gains@>@U: Whenever this SIGNI attacks, target SIGNI on your opponent's field gets --8000 power until end of turn unless your opponent discards three cards.@@until end of turn." +
                "~#You may discard a card. If you do, put target upped SIGNI on your opponent's field on the bottom of its owner's deck."
        );
        
        setName("en_fan", "Gate Drive");
        setDescription("en_fan",
                "Target 1 of your SIGNI on a SIGNI zone with a [[Gate]], and until end of turn, it gains:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power unless your opponent discards 3 cards from their hand.@@" +
                "~#Target 1 of your opponent's upped SIGNI, and you may discard 1 card from your hand. If you do, put it on the bottom of their deck."
        );

		setName("zh_simplified", "GATE　DRIVE");
        setDescription("zh_simplified", 
                "相同精灵区有[[大门]]的你的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@U :当这只精灵攻击时，对战对手的精灵1只作为对象，如果对战对手不把手牌3张舍弃，那么直到回合结束时为止，其的力量-8000。@@" +
                "~#对战对手的竖直状态的精灵1只作为对象，可以把手牌1张舍弃。这样做的场合，将其放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

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
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withZoneObject(CardUnderType.ZONE_GATE)));
        }
        private void onSpellEff()
        {
            if(spell.getTarget() != null)
            {
                AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                attachAbility(spell.getTarget(), attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private void onAttachedAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && discard(getOpponent(), 0,3, ChoiceLogic.BOOLEAN).get() == null)
            {
                getAbility().getSourceCardIndex().getIndexedInstance().gainPower(target, -8000, ChronoDuration.turnEnd());
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().SIGNI().upped()).get();
            
            if(target != null && discard(0,1).get() != null)
            {
                returnToDeck(target, DeckPosition.BOTTOM);
            }
        }
    }
}
