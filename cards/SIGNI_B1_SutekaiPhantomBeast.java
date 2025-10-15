package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B1_SutekaiPhantomBeast extends Card {

    public SIGNI_B1_SutekaiPhantomBeast()
    {
        setImageSets("SPDi01-123");

        setOriginalName("幻獣　ステカイ");
        setAltNames("ゲンジュウステカイ Genjuu Sutekai");
        setDescription("jp",
                "@C：あなたの手札が５枚以上あるかぎり、このシグニのパワーは＋5000される。\n" +
                "@U：あなたのターン終了時、このシグニのパワーが10000以上の場合、カードを１枚引く。"
        );

        setName("en", "Sutekai, Phantom Beast");
        setDescription("en",
                "@C: As long as there are 5 or more cards in your hand, this SIGNI gets +5000 power.\n" +
                "@U: At the end of your turn, if this SIGNI's power is 10000 or more, draw 1 card."
        );

		setName("zh_simplified", "幻兽 史德拉海牛");
        setDescription("zh_simplified", 
                "@C :你的手牌在5张以上时，这只精灵的力量+5000。\n" +
                "@U :你的回合结束时，这只精灵的力量在10000以上的场合，抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.EARTH_BEAST);
        setLevel(1);
        setPower(2000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) >= 5 ? ConditionState.OK : ConditionState.BAD;
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(getPower().getValue() >= 10000)
            {
                draw(1);
            }
        }
    }
}
