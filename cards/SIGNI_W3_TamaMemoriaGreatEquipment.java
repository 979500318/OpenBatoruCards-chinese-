package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition;
import open.batoru.data.ability.AbilityConst.Enter;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_TamaMemoriaGreatEquipment extends Card {

    public SIGNI_W3_TamaMemoriaGreatEquipment()
    {
        setImageSets("WXDi-P08-037", "WXDi-P08-037P");

        setOriginalName("大装　タマ//メモリア");
        setAltNames("タイソウタマメモリア Daisou Tama Memoria");
        setDescription("jp",
                """
                @C：このシグニが覚醒状態であるかぎり、このシグニのパワーは＋2000され、[[シャドウ（レベル３以上のシグニ）]]を得る。
                @U：このシグニがアタックしたとき、あなたのデッキの一番上を公開する。そのカードがシグニの場合、そのシグニとあなたのアップ状態のシグニ１体の場所を入れ替えてもよい。そのシグニの@E能力は発動しない。
                @U：このシグニがバトルによって対戦相手のシグニ１体をバニッシュしたとき、このシグニは覚醒する。"""
        );

        setName("en", "Tama//Memoria, Full Armed");
        setDescription("en",
                "@C: As long as this SIGNI is awakened, it gets +2000 power and gains [[Shadow -- Level three or more SIGNI]].\n" +
                "@U: Whenever this SIGNI attacks, reveal the top card of your deck. If that card is a SIGNI, you may swap an upped SIGNI on your field with it. The @E abilities of SIGNI put onto your field this way do not activate.\n" +
                "@U: Whenever this SIGNI vanishes a SIGNI on your opponent's field through battle, it is awakened."
        );
        
        setName("en_fan", "Tama//Memoria, Great Equipment");
        setDescription("en_fan",
                """
                 @C: As long as this SIGNI is awakened, this SIGNI gets +2000, and it gains [[Shadow (level 3 or higher SIGNI)]].
                 @U: Whenever this SIGNI attacks, reveal the top card of your deck. If it is a SIGNI, you may exchange the positions of that SIGNI and 1 of your upped SIGNI. That SIGNI's @E abilities don't activate.
                 @U: Whenever this SIGNI banishes 1 of your opponent's SIGNI in battle, this SIGNI awakens."""
        );

		setName("zh_simplified", "大装 小玉//回忆");
        setDescription("zh_simplified", 
                "@C :这只精灵在觉醒状态时，这只精灵的力量+2000，这只精灵得到[[暗影（等级3以上的精灵）]]。\n" +
                "@U 当这只精灵攻击时，你的牌组最上面公开。那张牌是精灵的场合，可以把那张精灵与你的竖直状态的精灵1只的场所交换。那只精灵的@E能力不能发动。\n" +
                "@U :当这只精灵因为战斗把对战对手的精灵1只破坏时，这只精灵觉醒。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ARM);
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

            registerConstantAbility(this::onConstEffCond, new PowerModifier(2000), new AbilityGainModifier(this::onConstEffModGetSample));

            registerAutoAbility(GameConst.GameEventId.ATTACK, this::onAutoEff1);

            AutoAbility auto2 = registerAutoAbility(GameConst.GameEventId.BANISH, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
        }

        private AbilityCondition.ConditionState onConstEffCond()
        {
            return isState(GameConst.CardStateFlag.AWAKENED) ? AbilityCondition.ConditionState.OK : AbilityCondition.ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private AbilityCondition.ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() >= 3 ? AbilityCondition.ConditionState.OK : AbilityCondition.ConditionState.BAD;
        }

        private void onAutoEff1()
        {
            CardIndex cardIndex = reveal();
            
            if(cardIndex != null)
            {
                if(CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()))
                {
                    CardIndex cardIndexOnField = playerTargetCard(0,1, new TargetFilter(TargetHint.FIELD).own().SIGNI().upped().playableAs(cardIndex)).get();
                    
                    if(cardIndexOnField != null)
                    {
                        returnToDeck(cardIndexOnField, DeckPosition.TOP);
                        if(!putOnField(cardIndex, cardIndexOnField.getPreTransientLocation(), Enter.DONT_ACTIVATE))
                        {
                            returnToDeck(cardIndex, DeckPosition.TOP);
                        }
                    } else {
                        returnToDeck(cardIndex, DeckPosition.TOP);
                    }
                } else {
                    returnToDeck(cardIndex, DeckPosition.TOP);
                }
            }
        }

        private AbilityCondition.ConditionState onAutoEff2Cond(CardIndex caller)
        {
            return !isOwnCard(caller) &&
                    getEvent().getSourceCardIndex() == getCardIndex() && getEvent().getSourceAbility() == null ? AbilityCondition.ConditionState.OK : AbilityCondition.ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            getCardStateFlags().addValue(GameConst.CardStateFlag.AWAKENED);
        }
    }
}
