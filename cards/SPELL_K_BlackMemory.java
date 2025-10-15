package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_K_BlackMemory extends Card {
    
    public SPELL_K_BlackMemory()
    {
        setImageSets("WXDi-P07-095");
        setLinkedImageSets("WXDi-P07-047");
        
        setOriginalName("ブラック・メモリー");
        setAltNames("ブラックメモリー Burakku Memorii");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのトラッシュからレベル２以下のシグニを２枚まで対象とし、それらを場に出す。\n" +
                "$$2あなたのトラッシュから《惨之遊姫　グズ子//メモリア》とレベル２以下のシグニをそれぞれ１枚まで対象とし、それらを場に出す。" +
                "~#：あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Black Memory");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 Put up to two target level two or less SIGNI from your trash onto your field.\n" +
                "$$2 Put up to one target \"Guzuko//Memoria, Tragic Party Queen\" and one target level two or less SIGNI from your trash onto your field." +
                "~#Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "Black Memory");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 level 2 or lower SIGNI from your trash, and put them onto the field.\n" +
                "$$2 Target up to 1 \"Guzuko//Memoria, Wretched Play Princess\" and up to 1 level 2 or lower SIGNI from your trash, and put them onto the field." +
                "~#Target 1 of your SIGNI without #G @[Guard]@ from your trash, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "漆黑·追思");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 从你的废弃区把等级2以下的精灵2张最多作为对象，将这些出场。\n" +
                "$$2 从你的废弃区把《惨之遊姫　グズ子//メモリア》与等级2以下的精灵各1张最多作为对象，将这些出场。" +
                "~#从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        
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
            spell.setModeChoice(1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            if(spell.getChosenModes() == 1)
            {
                spell.setTargets(playerTargetCard(0,2, new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(0,2).fromTrash().playable()));
            } else {
                DataTable<CardIndex> data = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().withName("惨之遊姫　グズ子//メモリア").playable());
                data.add(playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromTrash().withLevel(0,2).playable()).get());
                spell.setTargets(data);
            }
        }
        private void onSpellEff()
        {
            putOnField(spell.getTargets());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
            
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
