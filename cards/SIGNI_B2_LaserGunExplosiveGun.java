package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B2_LaserGunExplosiveGun extends Card {

    public SIGNI_B2_LaserGunExplosiveGun()
    {
        setImageSets("WX25-P1-085");

        setOriginalName("爆砲　レーザーガン");
        setAltNames("バクホウレーザーガン Bakuhou Reezaagan");
        setDescription("jp",
                "@U $T1：あなたのシグニが#Hしたとき、カードを１枚引く。" +
                "~#：対戦相手のルリグ１体を対象とし、それをダウンする。"
        );

        setName("en", "Laser Gun, Explosive Gun");
        setDescription("en",
                "@U $T1: When your SIGNI reach #H @[Heaven]@, draw 1 card." +
                "~#Target 1 of your opponent's LRIG, and down it."
        );

		setName("zh_simplified", "爆炮 激光枪");
        setDescription("zh_simplified", 
                "@U $T1 :当你的精灵达成[天堂]时，抽1张牌。" +
                "~#对战对手的分身1只作为对象，将其横置。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WEAPON);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.HEAVEN, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            auto.setUseLimit(UseLimit.TURN, 1);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            draw(1);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.DOWN).OP().anyLRIG()).get();
            down(target);
        }
    }
}
