package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventAbilityGain;
import open.batoru.data.ability.stock.StockAbility;
import open.batoru.data.ability.stock.StockAbilityAssassin;
import open.batoru.data.ability.stock.StockAbilityDoubleCrush;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_R3_AlexandriteNaturalPyroxene extends Card {
    
    public SIGNI_R3_AlexandriteNaturalPyroxene()
    {
        setImageSets("WXDi-P04-035");
        
        setOriginalName("羅輝石　アレキサンドライト");
        setAltNames("ラキセキアレキサンドライト Rakiseki Arekisandoraito");
        setDescription("jp",
                "@U：あなたの他のシグニ１体が[[アサシン]]１つか[[ランサー]]１つか[[ダブルクラッシュ]]１つを得たとき、%X %Xを支払ってもよい。そうした場合、ターン終了時まで、このシグニはその能力を得る。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のアップ状態のシグニ１体を対象とし、それをバニッシュする。\n" +
                "$$2カードを１枚引く。"
        );
        
        setName("en", "Alexandrite, Natural Pyroxene");
        setDescription("en",
                "@U: Whenever another SIGNI on your field gains [[Assassin]], [[Lancer]], or [[Double Crush]], you may pay %R %X. If you do, this SIGNI gains that ability until end of turn." +
                "~#Choose one -- \n$$1 Vanish target upped SIGNI on your opponent's field. \n$$2 Draw a card."
        );
        
        setName("en_fan", "Alexandrite, Natural Pyroxene");
        setDescription("en_fan",
                "@U: Whenever 1 of your other SIGNI gains 1 instance of [[Assassin]], 1 instance of [[Lancer]], or 1 instance of [[Double Crush]], you may pay %R %X. If you do, until end of turn, this SIGNI gains that ability." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target 1 of your opponent's upped SIGNI, and banish it.\n" +
                "$$2 Draw 1 card."
        );
        
		setName("zh_simplified", "罗辉石 亚历山大石");
        setDescription("zh_simplified", 
                "@U :当你的其他的精灵1只得到[[暗杀]]1个或[[枪兵]]1个或[[双重击溃]]1个时，可以支付%R%X。这样做的场合，直到回合结束时为止，这只精灵得到那个能力。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的竖直状态的精灵1只作为对象，将其破坏。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.GEM);
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
            
            AutoAbility auto = registerAutoAbility(GameEventId.ABILITY_GAIN, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && caller != getCardIndex() && CardType.isSIGNI(caller.getCardReference().getType()) &&
                   EventAbilityGain.getDataGainedAbility().getSourceStockAbility() != null &&
                   (EventAbilityGain.getDataGainedAbility().getSourceStockAbility() instanceof StockAbilityAssassin ||
                    EventAbilityGain.getDataGainedAbility().getSourceStockAbility() instanceof StockAbilityLancer ||
                    EventAbilityGain.getDataGainedAbility().getSourceStockAbility() instanceof StockAbilityDoubleCrush) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(payEner(Cost.color(CardColor.RED, 1) + Cost.colorless(1)))
            {
                StockAbility sourceGainedAbility = EventAbilityGain.getDataGainedAbility().getSourceStockAbility();
                
                StockAbility attachedStock;
                if(sourceGainedAbility instanceof StockAbilityAssassin gainedAssassin) attachedStock = new StockAbilityAssassin(gainedAssassin);
                else if(sourceGainedAbility instanceof StockAbilityLancer gainedLancer) attachedStock = new StockAbilityLancer(gainedLancer);
                else attachedStock = new StockAbilityDoubleCrush();
                
                attachAbility(getCardIndex(), attachedStock, ChronoDuration.turnEnd());
            }
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
