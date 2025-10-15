package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;
import open.batoru.data.ability.cost.TrashCost;

public final class SPELL_K_GraveAppear extends Card {

    public SPELL_K_GraveAppear()
    {
        setImageSets("WX25-P1-110");

        setOriginalName("グレイブ・アピアー");
        setAltNames("グレイブアピアー Gureibu Apiaa");
        setDescription("jp",
                "このスペルを使用する際、あなたのレベル２以下の＜古代兵器＞のシグニ１体を場からトラッシュに置いてもよい。そうした場合、このスペルの使用コストは%K減る。\n\n" +
                "あなたのトラッシュから＜古代兵器＞のシグニ１枚を対象とし、それを場に出す。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );

        setName("en", "Grave Appear");
        setDescription("en",
                "While using this spell, you may put 1 level 2 or lower <<Ancient Weapon>> SIGNI from your field into the trash. If you do, the use cost of this spell is reduced by %K.\n\n" +
                "Target 1 <<Ancient Weapon>> SIGNI from your trash, and put it onto the field." +
                "~#Target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );

		setName("zh_simplified", "墓地·唤醒");
        setDescription("zh_simplified", 
                "这张魔法使用时，可以把你的等级2以下的<<古代兵器>>精灵1只从场上放置到废弃区。这样做的场合，这张魔法的使用费用减%K。\n" +
                "从你的废弃区把<<古代兵器>>精灵1张作为对象，将其出场。" +
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
            spell.setReductionCost(new TrashCost(0,1, new TargetFilter().SIGNI().withLevel(0,2).withClass(CardSIGNIClass.ANCIENT_WEAPON)), Cost.color(CardColor.BLACK, 1));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withClass(CardSIGNIClass.ANCIENT_WEAPON).fromTrash().playable()));
        }
        private void onSpellEff()
        {
            putOnField(spell.getTarget());
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().guard()).fromTrash()).get();
            
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

