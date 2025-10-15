package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
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
import open.batoru.game.FieldZone;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIG_B3_UmrTreWielderOfTheKeyOfPerformingShout extends Card {
    
    public LRIG_B3_UmrTreWielderOfTheKeyOfPerformingShout()
    {
        setImageSets("WXDi-P05-016", Mask.IGNORE+"WXDi-P114");
        setLinkedImageSets(Token_Hastalyk.IMAGE_SET);
        
        setOriginalName("奏叫の鍵主　ウムル＝トレ");
        setAltNames("ソウキョウノカギヌシウムルトレ Soukyou no Kaginushi Umuru Tore");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのシグニ２体を対象とし、それらの場所を入れ替えてもよい。\n" +
                "@E：対戦相手の手札を見て１枚選び、デッキの一番下に置く。\n" +
                "@A $G1 %B0：対戦相手のシグニゾーン１つに[[ハスターリク]]１つを置く。"
        );
        
        setName("en", "Umr =Tre=, Key to Uproar");
        setDescription("en",
                "@U: At the beginning of your attack phase, you may swap the positions of two target SIGNI on your field.\n" +
                "@E: Look at your opponent's hand and choose a card. Put that card on the bottom of its owner's deck.\n" +
                "@A $G1 %B0: Put one [[Hastalyk]] on one of your opponent's SIGNI Zones."
        );
        
        setName("en_fan", "Umr-Tre, Wielder of the Key of Performing Shout");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, target 2 of your SIGNI, and you may exchange their positions.\n" +
                "@E: Look at your opponent's hand, choose 1 card from it, and put it on the bottom of their deck.\n" +
                "@A $G1 %B0: Put 1 [[Hastalyk]] on 1 of your opponent's SIGNI zones."
        );
        
		setName("zh_simplified", "奏叫的键主 乌姆尔=TRE");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的精灵2只作为对象，可以将这些的场所交换。\n" +
                "@E :看对战对手的手牌选1张，放置到牌组最下面。\n" +
                "@A $G1 %B0:对战对手的精灵区1个放置[[哈斯陶吕克]]1个。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.UMR);
        setLRIGTeam(CardLRIGTeam.ANCIENT_SURPRISE);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 2));
        setLevel(3);
        setLimit(6);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            ActionAbility act = registerActionAbility(new EnerCost(Cost.color(CardColor.BLUE, 0)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(2, new TargetFilter(TargetHint.MOVE).own().SIGNI());
            
            if(data.size() == 2 && playerChoiceActivate())
            {
                exchange(data.get(0), data.get(1));
            }
        }
        
        private void onEnterEff()
        {
            reveal(getHandCount(getOpponent()), getOpponent(), CardLocation.HAND, true);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).OP().fromRevealed()).get();
            returnToDeck(cardIndex, DeckPosition.BOTTOM);
            
            addToHand(getCardsInRevealed(getOpponent()));
        }
        
        private void onActionEff()
        {
            FieldZone fieldZone = playerTargetZone(new TargetFilter(TargetHint.ZONE).OP().SIGNI()).get();
            attachZoneObject(fieldZone, CardUnderType.ZONE_HASTALYK);
        }
    }
}
