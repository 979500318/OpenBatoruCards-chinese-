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
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_G_MassProduction extends Card {
    
    public SPELL_G_MassProduction()
    {
        setImageSets("WXDi-P08-074");
        setLinkedImageSets("WXDi-P08-046","WXDi-P08-062","WXDi-P08-058");
        
        setOriginalName("量産");
        setAltNames("リョウサン Ryousan");
        setDescription("jp",
                "以下の２つから１つを選ぶ。\n" +
                "$$1あなたのエナゾーンから緑のシグニ１枚を対象とし、それを場に出す。\n" +
                "$$2あなたのエナゾーンから《コードラビリンス　アト//メモリア》か《コードメイズ　ウムル//メモリア》か《紅魔　タウィル//メモリア》１枚を対象とし、それを場に出す。次の対戦相手のターン終了時まで、それのパワーを＋3000する。" +
                "~#：【エナチャージ１】をする。その後、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "Fabricate");
        setDescription("en",
                "Choose one of the following.\n" +
                "$$1 Put target green SIGNI from your Ener Zone onto your field.\n" +
                "$$2 Put target \"At//Memoria, Code: Labyrinth\", \"Umr//Memoria, Code: Maze\", or \"Tawil//Memoria, Crimson Evil\" from your Ener Zone onto your field. It gets +3000 power until the end of your opponent's next end phase." +
                "~#[[Ener Charge 1]]. Then, add up to one target SIGNI from your Ener Zone to your hand or put it onto your field."
        );
        
        setName("en_fan", "Mass Production");
        setDescription("en_fan",
                "@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 green SIGNI from your ener zone, and put it onto the field.\n" +
                "$$2 Target 1 \"Code Labyrinth At//Memoria\", \"Code Maze Umr//Memoria\", or \"Tawil//Memoria, Crimson Devil\" from your ener zone, and put it onto the field. Until the end of your opponent's next turn, it gets +3000 power." +
                "~#[[Ener Charge 1]]. Then, target up to 1 SIGNI from your ener zone, and add it to your hand or put it onto the field."
        );
        
		setName("zh_simplified", "量产");
        setDescription("zh_simplified", 
                "从以下的2种选1种。\n" +
                "$$1 从你的能量区把绿色的精灵1张作为对象，将其出场。\n" +
                "$$2 从你的能量区把《コードラビリンス　アト//メモリア》或《コードメイズ　ウムル//メモリア》或《紅魔　タウィル//メモリア》1张作为对象，将其出场。直到下一个对战对手的回合结束时为止，其的力量+3000。" +
                "~#[[能量填充1]]。然后，从你的能量区把精灵1张最多作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.GREEN);
        
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
            TargetFilter filter = new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable();
            filter = spell.getChosenModes() == 1 ? filter.withColor(CardColor.GREEN) : filter.withName("コードラビリンス　アト//メモリア", "コードメイズ　ウムル//メモリア", "紅魔　タウィル//メモリア");
            
            spell.setTargets(playerTargetCard(filter));
        }
        private void onSpellEff()
        {
            CardIndex target = spell.getTarget();
            putOnField(target);
            
            if(spell.getChosenModes() == 2)
            {
                gainPower(target, 3000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
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
