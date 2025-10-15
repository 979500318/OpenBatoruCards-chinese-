package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;

public final class SIGNI_G1_FlemingVerdantWisdom extends Card {

    public SIGNI_G1_FlemingVerdantWisdom()
    {
        setImageSets("WXDi-P09-072");

        setOriginalName("翠英　フレミング");
        setAltNames("スイエイフレミング Suiei Furemingu");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのライフクロスが５枚の場合、【エナチャージ１】をする。４枚の場合、カードを１枚引く。"
        );

        setName("en", "Fleming, Jade Wisdom");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have exactly five cards in your Life Cloth, [[Ener Charge 1]]. If you have exactly four cards in your Life Cloth, draw a card."
        );
        
        setName("en_fan", "Fleming, Verdant Wisdom");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if you have 5 life cloth, [[Ener Charge 1]]. If you have 4 life cloth, draw 1 card."
        );

		setName("zh_simplified", "翠英 弗莱明");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的生命护甲在5张的场合，[[能量填充1]]。4张的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(1);
        setPower(2000);

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
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getLifeClothCount(getOwner()) == 5)
            {
                enerCharge(1);
            } else if(getLifeClothCount(getOwner()) == 4)
            {
                draw(1);
            }
        }
    }
}
