package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.CardDataColor;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.ModifiableValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_K3_TamaMemoriaWickedAngelPrincess extends Card {
    
    public SIGNI_K3_TamaMemoriaWickedAngelPrincess()
    {
        setImageSets("WXDi-P06-040", "WXDi-P06-040P", "SPDi10-07");
        
        setOriginalName("凶天姫　タマ//メモリア");
        setAltNames("キョウテンヒメタマメモリア Kyoutenhime Tama Memoria");
        setDescription("jp",
                "@C：あなたのトラッシュにカードが１０枚以上あるかぎり、このシグニのパワーは＋3000され、このシグニは[[シャドウ（レベル２以下のシグニ）]]を得る。\n" +
                "@C：あなたの、場とエナゾーンにあるシグニは追加で黒を得る。\n" +
                "@U $T1：あなたのメインフェイズ以外であなたの黒のシグニ１体が場を離れたとき、対戦相手のデッキの上からカードを２枚トラッシュに置く。" +
                "~#：対戦相手のレベル２以下のシグニ１体を対象とし、それをバニッシュする。"
        );
        
        setName("en", "Tama//Memoria, Doomed Angel Queen");
        setDescription("en",
                "@C: As long as you have ten or more cards in your trash, this SIGNI gets +3000 power and gains [[Shadow -- Level two or less SIGNI]].\n" +
                "@C: SIGNI on your field and Ener Zone are additionally black.\n" +
                "@U $T1: When a black SIGNI leaves your field outside of your main phase, put the top two cards of your opponent's deck into their trash." +
                "~#Vanish target level two or less SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Tama//Memoria, Wicked Angel Princess");
        setDescription("en_fan",
                "@C: As long as there are 10 or more cards in your trash, this SIGNI gets +3000 power and it gains [[Shadow (level 2 or lower SIGNI)]].\n" +
                "@C: All of the SIGNI on your field and in your ener zone are also black.\n" +
                "@U $T1: When 1 of your black SIGNI leaves the field other than during your main phase, put the top 2 cards of your opponent's deck into the trash." +
                "~#Target 1 of your opponent's level 2 or lower SIGNI, and banish it."
        );
        
		setName("zh_simplified", "凶天姬 小玉//回忆");
        setDescription("zh_simplified", 
                "@C :你的废弃区的牌在10张以上时，这只精灵的力量+3000，这只精灵得到[[暗影（等级2以下的精灵）]]。\n" +
                "@C :你的，场上和能量区的精灵追加得到黑色。\n" +
                "@U $T1 :当在你的主要阶段以外把你的黑色的精灵1只离场时，从对战对手的牌组上面把2张牌放置到废弃区。" +
                "~#对战对手的等级2以下的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
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
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000),new AbilityGainModifier(this::onConstEff1ModGetSample));
            
            registerConstantAbility(
                new TargetFilter().own().SIGNI().fromLocation(
                    CardLocation.SIGNI_LEFT,CardLocation.SIGNI_CENTER,CardLocation.SIGNI_RIGHT,CardLocation.CHEER,
                    CardLocation.ENER
                ),
                new ModifiableValueModifier<>(this::onConstEff2ModGetSample, () -> CardColor.BLACK)
            );
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getTrashCount(getOwner()) >= 10 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEff1ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private CardDataColor onConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getColor();
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return (!isOwnTurn() || getCurrentPhase() != GamePhase.MAIN) &&
                    caller.isSIGNIOnField() && !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) && 
                    isOwnCard(caller) && caller.getIndexedInstance().getColor().matches(CardColor.BLACK) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            millDeck(getOpponent(), 2);
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withLevel(0,2)).get();
            banish(target);
        }
    }
}
