package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G1_VassagoVerdantDevil extends Card {
    
    public SIGNI_G1_VassagoVerdantDevil()
    {
        setImageSets("WXDi-P01-071", "SPDi38-09");
        
        setOriginalName("翠魔　ヴァサゴ");
        setAltNames("スイマヴァサゴ Suima Vasago");
        setDescription("jp",
                "@U：このシグニがコストか効果によって場からトラッシュに置かれたとき、【エナチャージ１】をする。\n" +
                "@E：あなたのシグニ１体を対象とし、ターン終了時まで、それのパワーを＋3000する。あなたのデッキの一番上のカードをトラッシュに置く。"
        );
        
        setName("en", "Vassago, Jade Evil");
        setDescription("en",
                "@U: When this SIGNI is put into the trash from the field by a cost or an effect, [[Ener Charge 1]].\n" +
                "@E: Target SIGNI on your field gets +3000 power until end of turn. Put the top card of your deck into your trash."
        );
        
        setName("en_fan", "Vassago, Verdant Devil");
        setDescription("en_fan",
                "@U: When this SIGNI is put from the field into the trash by a cost or effect, [[Ener Charge 1]].\n" +
                "@E: Target 1 of your SIGNI, and until end of turn, it gets +3000 power. Put the top card of your deck into the trash."
        );
        
		setName("zh_simplified", "翠魔 瓦沙克");
        setDescription("zh_simplified", 
                "@U :当这只精灵因为费用或效果从场上放置到废弃区时，[[能量填充1]]。\n" +
                "@E :你的精灵1只作为对象，直到回合结束时为止，其的力量+3000。你的牌组最上面的牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(2000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return getEvent().getSourceAbility() != null && cardIndex.isSIGNIOnField() ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            enerCharge(1);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI()).get();
            gainPower(target, 3000, ChronoDuration.turnEnd());
            
            millDeck(1);
        }
    }
}
