package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionReturnToDeck;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_G3_CodeLabyrinthAtMemoria extends Card {
    
    public SIGNI_G3_CodeLabyrinthAtMemoria()
    {
        setImageSets("WXDi-P08-046", "WXDi-P08-046P");
        
        setOriginalName("コードラビリンス　アト//メモリア");
        setAltNames("コードラビリンスアトメモリア Koodo Rabirinsu Ato Memoria");
        setDescription("jp",
                "@C：このシグニが場を離れる場合、代わりにこれをデッキの一番下に置いてもよい。\n" +
                "@U：このシグニが場を離れたとき、あなたのデッキをシャッフルしてもよい。そうした場合、あなたのデッキの上から、カードを２枚トラッシュに置きカードを４枚を見る。その見たカードの中から《コードラビリンス アト//メモリア》を１枚までダウン状態で場に出し、残りを好きな順番でデッキの一番下に置く。" +
                "~#：あなたのデッキの一番上と一番下を見る。その中からシグニを１枚まで場に出し、残りを手札に加える。"
        );
        
        setName("en", "At//Memoria, Code: Labyrinth");
        setDescription("en",
                "@C: If this SIGNI would leave the field, instead you may put this SIGNI on the bottom of its owner's deck.\n" +
                "@U: When this SIGNI leaves the field, you may shuffle your deck. If you do, put the top two cards of your deck into your trash and look at the next four cards. Put up to one \"At//Memoria, Code: Labyrinth\" onto your field downed from among them. Put the rest on the bottom of your deck in any order." +
                "~#Look at the top and bottom card of your deck. Put up to one SIGNI from among them onto your field. Add the rest to your hand."
        );
        
        setName("en_fan", "Code Labyrinth At//Memoria");
        setDescription("en_fan",
                "@C: If this SIGNI would leave the field, you may put it on the bottom of your deck instead.\n" +
                "@U: When this SIGNI leaves the field, you may shuffle your deck. If you do, put the top 2 cards of your deck into the trash, and look at the top 4 cards of your deck. Put up to 1 \"Code Labyrinth At//Memoria\" from among the cards you are looking at onto the field downed, and put the rest on the bottom of your deck in any order." +
                "~#Look at the top card and the bottom card of your deck. Put up to 1 SIGNI from among them onto the field, and add the rest to your hand."
        );
        
		setName("zh_simplified", "迷牢代号 亚特//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵离场的场合，作为替代，可以将此牌放置到牌组最下面。\n" +
                "@U :当这只精灵离场时，可以把你的牌组洗切。这样做的场合，从你的牌组上面把，2张牌放置到废弃区并看4张牌。从看的牌中把《コードラビリンス　アト//メモリア》1张最多以横置状态出场，剩下的任意顺序放置到牌组最下面。" +
                "~#看你的牌组最上面和最下面。从中把精灵1张最多出场，剩下的加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
        setLevel(3);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data -> {
                return new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER,OverrideFlag.NON_MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler);
            }));
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return CardLocation.isSIGNI(getCardIndex().getLocation()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionReturnToDeck(list.getSourceEvent().getCallerCardIndex(), DeckPosition.BOTTOM));
        }
        
        private ConditionState onAutoEffCond()
        {
            return !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(playerChoiceActivate() && shuffleDeck())
            {
                millDeck(2);
                
                look(4);
                
                CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().withName("コードラビリンス　アト//メモリア").fromLooked().playable()).get();
                putOnField(cardIndex, Enter.DOWNED);
                
                while(getLookedCount() > 0)
                {
                    cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                    returnToDeck(cardIndex, DeckPosition.BOTTOM);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            look(CardLocation.DECK_MAIN, getDeckCount(getOwner())-1);
            look(CardLocation.DECK_MAIN, 0);
            
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().fromLooked().playable()).get();
            putOnField(cardIndex);
            
            addToHand(getCardsInLooked(getOwner()));
        }
    }
}
