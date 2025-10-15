package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.CardAbilities;

public final class SIGNI_G2_ZweiSangaMemoria extends Card {

    public SIGNI_G2_ZweiSangaMemoria()
    {
        setImageSets("WXDi-P10-068", "WXDi-P10-068P");

        setOriginalName("ツヴァイ＝サンガ//メモリア");
        setAltNames("ツヴァイサンガメモリア Tsuvai Sanga Memoria");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場にあるシグニが持つ色が合計３種類以上ある場合、あなたのエナゾーンからシグニを１枚まで対象とし、それを手札に加える。" +
                "~#：【エナチャージ１】をする。このターン、次にあなたがルリグによってダメージを受ける場合、代わりにダメージを受けない。"
        );

        setName("en", "Sanga//Memoria Type: Zwei");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you have three or more different colors among SIGNI on your field, add up to one target SIGNI from your Ener Zone to your hand." +
                "~#[[Ener Charge 1]]. The next time you would take damage from a LRIG this turn, instead you do not take that damage."
        );
        
        setName("en_fan", "Zwei-Sanga//Memoria");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if there are 3 or more colors among SIGNI on your field, target up to 1 SIGNI from your ener zone, and add it to your hand." +
                "~#[[Ener Charge 1]]. This turn, the next time you would be damaged by a LRIG, instead you aren't damaged."
        );

		setName("zh_simplified", "ZWEI=山河//回忆");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上的精灵持有颜色在合计3种类以上的场合，从你的能量区把精灵1张最多作为对象，将其加入手牌。" +
                "~#[[能量填充1]]。这个回合，下一次你因为分身受到伤害的场合，作为替代，不会受到伤害。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VENOM_FANG);
        setLevel(2);
        setPower(5000);

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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(CardAbilities.getColorsCount(getSIGNIOnField(getOwner())) >= 3)
            {
                CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().fromEner()).get();
                addToHand(target);
            }
        }
        
        private void onLifeBurstEff()
        {
            enerCharge(1);

            blockNextDamage(cardIndexSnapshot -> CardType.isLRIG(cardIndexSnapshot.getCardReference().getType()));
        }
    }
}
