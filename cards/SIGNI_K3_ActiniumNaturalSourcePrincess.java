package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_ActiniumNaturalSourcePrincess extends Card {
    
    public SIGNI_K3_ActiniumNaturalSourcePrincess()
    {
        setImageSets("WXDi-P03-046");
        
        setOriginalName("羅原姫　Ａｃ");
        setAltNames("ラゲンヒメアクチニウム Ragenhime Akuchiniumu");
        setDescription("jp",
                "@U：あなたのターンの間、対戦相手のルリグがグロウしたとき、あなたのトラッシュから黒のシグニ１枚を対象とし、それを手札に加える。\n" +
                "@A $T1 %K：あなたのデッキの上からカードを５枚トラッシュに置く。その後、あなたのトラッシュから黒のシグニ１枚を対象とし、それを場に出す。"
        );
        
        setName("en", "Ac, Natural Element Queen");
        setDescription("en",
                "@U: During your turn, whenever a LRIG on your opponent's field grows, add target black SIGNI from your trash to your hand.\n" +
                "@A $T1 %K: Put the top five cards of your deck into your trash. Then, put target black SIGNI from your trash onto your field."
        );
        
        setName("en_fan", "Actinium, Natural Source Princess");
        setDescription("en_fan",
                "@U: During your turn, whenever your opponent's LRIG grows, target 1 black SIGNI from your trash, and add it to your hand.\n" +
                "@A $T1 %K: Put the top 5 cards of your deck into the trash. Then, target 1 black SIGNI from your trash, and put it onto the field."
        );
        
		setName("zh_simplified", "罗原姬 Ac");
        setDescription("zh_simplified", 
                "@U :你的回合期间，当对战对手的分身成长时，从你的废弃区把黑色的精灵1张作为对象，将其加入手牌。\n" +
                "@A $T1 %K:从你的牌组上面把5张牌放置到废弃区。然后，从你的废弃区把黑色的精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ATOM);
        setLevel(3);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.GROW, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnTurn() && !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withColor(CardColor.BLACK).fromTrash()).get();
            addToHand(target);
        }
        
        private void onActionEff()
        {
            millDeck(5);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(CardColor.BLACK).playable().fromTrash()).get();
            putOnField(target);
        }
    }
}
