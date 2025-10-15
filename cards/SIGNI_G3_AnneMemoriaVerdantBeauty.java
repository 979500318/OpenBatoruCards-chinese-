package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_G3_AnneMemoriaVerdantBeauty extends Card {
    
    public SIGNI_G3_AnneMemoriaVerdantBeauty()
    {
        setImageSets("WXDi-P06-038", "WXDi-P06-038P");
        
        setOriginalName("翠美姫　アン//メモリア");
        setAltNames("スイビキアンメモリア Suibiki An Memoria");
        setDescription("jp",
                "@U $T1：あなたのエナゾーンから効果によってカード１枚が他の領域に移動したとき、【エナチャージ１】をする。\n" +
                "@E %W：次の対戦相手のターン終了時まで、このシグニのパワーを＋2000し、このシグニは[[シャドウ（レベル３以上のシグニ）]]を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Ann//Memoria, Jade Beauty Queen");
        setDescription("en",
                "@U $T1: When a card moves from your Ener Zone to another Zone by an effect, [[Ener Charge 1]].\n" +
                "@E %W: This SIGNI gets +2000 power and gains [[Shadow -- Level three or more SIGNI]] until the end of your opponent's next end phase." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Anne//Memoria, Verdant Beauty");
        setDescription("en_fan",
                "@U $T1: When a card is moved from your ener zone to another zone by an effect, [[Ener Charge 1]].\n" +
                "@E %W: Until the end of your opponent's next turn, this SIGNI gets +2000 power and it gains [[Shadow (level 3 or higher SIGNI)]]." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "翠美姬 安//回忆");
        setDescription("zh_simplified", 
                "@U $T1 :当因为效果从你的能量区把1张牌往其他的领域移动时，[[能量填充1]]。\n" +
                "@E %W:直到下一个对战对手的回合结束时为止，这只精灵的力量+2000，这只精灵得到[[暗影（等级3以上的精灵）]]。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setActiveLocation(CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT, CardLocation.ENER);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            auto.enableUnifiedMoveEvents();
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1)), this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller.isEffectivelyAtLocation(CardLocation.ENER) &&
                   getEvent().getSourceAbility() != null && getEvent().getSourceCost() == null &&
                   (CardLocation.isSIGNI(getCardIndex().getLocation()) || (getCardIndex() == caller && CardLocation.isSIGNI(EventMove.getDataMoveLocation()))) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            enerCharge(1);
        }
        
        private void onEnterEff()
        {
            gainPower(getCardIndex(), 2000, ChronoDuration.nextTurnEnd(getOpponent()));
            
            attachAbility(getCardIndex(), new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() >= 3 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().upped()).get();
                banish(target);
            } else {
                draw(1);
            }
        }
    }
}
