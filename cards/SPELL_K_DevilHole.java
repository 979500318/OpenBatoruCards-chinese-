package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_DevilHole extends Card {

    public SPELL_K_DevilHole()
    {
        setImageSets("WX24-P3-093");

        setOriginalName("デビル・ホール");
        setAltNames("デビルホール Debiru Hooru");
        setDescription("jp",
                "あなたのトラッシュから＜悪魔＞のシグニ１枚を対象とし、それを場に出す。\n\n" +
                "@U：このカードが効果によってデッキからトラッシュに置かれたとき、あなたのエナゾーンから＜悪魔＞のシグニ１枚をトラッシュに置いてもよい。そうした場合、このカードをトラッシュから手札に加える。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Devil Hole");
        setDescription("en",
                "Target 1 <<Devil>> SIGNI from your trash, and put it onto the field.\n\n" +
                "@U: When this card is put from your deck into the trash by an effect, you may put 1 <<Devil>> SIGNI from your ener zone into the trash. If you do, add this card from your trash to your hand." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "恶魔·之洞");
        setDescription("zh_simplified", 
                "从你的废弃区把<<悪魔>>精灵1张作为对象，将其出场。\n" +
                "@U :当这张牌因为效果从牌组放置到废弃区时，可以从你的能量区把<<悪魔>>精灵1张放置到废弃区。这样做的场合，这张牌从废弃区加入手牌。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

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

            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setActiveLocation(CardLocation.DECK_MAIN);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromTrash()));
        }
        private void onSpellEff()
        {
            putOnField(spell.getTarget());
        }
        
        private ConditionState onAutoEffCond()
        {
            return getEvent().getSourceAbility() != null && getEvent().getSourceCost() == null ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH)
            {
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withClass(CardSIGNIClass.DEVIL).fromEner()).get();
                
                if(cardIndex != null)
                {
                    trash(cardIndex);
                    
                    addToHand(getCardIndex());
                }
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
            if(playerChoiceAction(ActionHint.HAND, ActionHint.FIELD) == 1)
            {
                addToHand(target);
            } else {
                putOnField(target);
            }
        }
    }
}
