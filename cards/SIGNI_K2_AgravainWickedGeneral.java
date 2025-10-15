package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_AgravainWickedGeneral extends Card {
    
    public SIGNI_K2_AgravainWickedGeneral()
    {
        setImageSets("WXDi-P07-091");
        
        setOriginalName("凶将　アグラヴェイン");
        setAltNames("キョウショウアグラヴェイン Kyoushou Aguravein");
        setDescription("jp",
                "=R あなたの＜武勇＞のシグニ１体の上に置く\n\n" +
                "@E %K：以下の２つから１つを選ぶ。\n" +
                "$$1対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2あなたのトラッシュから＜武勇＞のシグニ１枚を対象とし、それを手札に加える。"
        );
        
        setName("en", "Agravain, Doomed General");
        setDescription("en",
                "=R Place on top of a <<Brave>> SIGNI on your field. \n" +
                "@E %K: Choose one of the following.\n" +
                "$$1 Vanish target level one SIGNI on your opponent's field.\n" +
                "$$2 Add target <<Brave>> SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Agravein, Wicked General");
        setDescription("en_fan",
                "=R Put on 1 of your <<Valor>> SIGNI\n\n" +
                "@E %K: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's level 1 SIGNI, and banish it.\n" +
                "$$2 Target 1 <<Valor>> SIGNI from your trash, and add it to your hand."
        );
        
		setName("zh_simplified", "凶将 亚格拉宾");
        setDescription("zh_simplified", 
                "=R在你的<<武勇>>精灵1只的上面放置（这个条件没有满足则不能出场）\n" +
                "@E %K:从以下的2种选1种。\n" +
                "$$1 对战对手的等级1的精灵1只作为对象，将其破坏。\n" +
                "$$2 从你的废弃区把<<武勇>>精灵1张作为对象，将其加入手牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            setUseCondition(UseCondition.RISE, 1, new TargetFilter().withClass(CardSIGNIClass.VALOR));
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(1)).get();
                banish(target);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VALOR).fromTrash()).get();
                addToHand(target);
            }
        }
    }
}
