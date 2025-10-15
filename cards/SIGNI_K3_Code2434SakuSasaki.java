package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_K3_Code2434SakuSasaki extends Card {

    public SIGNI_K3_Code2434SakuSasaki()
    {
        setImageSets("WXDi-CP01-029");

        setOriginalName("コード２４３４　笹木咲");
        setAltNames("コードニジサンジササキサク Koodo Nijisanji Sasaki Saku");
        setDescription("jp",
                "@C：あなたのターンの間、このシグニのパワーはあなたのトラッシュにある＜バーチャル＞のシグニ５枚につき＋1000される。\n" +
                "@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それとこのシグニのパワーをそれぞれ－12000する。\n" +
                "@E：あなたのデッキの一番上のカードをトラッシュに置く。そのカードが＜バーチャル＞のシグニでない場合、このシグニをダウンする。"
        );

        setName("en", "Sasaki Saku, Code 2434");
        setDescription("en",
                "@C: During your turn, this SIGNI gets +1000 power for every five <<Virtual>> SIGNI in your trash.\n@U: Whenever this SIGNI attacks, you may pay %K. If you do, target SIGNI on your opponent's field and this SIGNI get --12000 power until end of turn.\n@E: Put the top card of your deck into your trash. If that card is not a <<Virtual>> SIGNI, down this SIGNI."
        );
        
        setName("en_fan", "Code 2434 Saku Sasaki");
        setDescription("en_fan",
                "@C: During your turn, this SIGNI gets +1000 power for every 5 <<Virtual>> SIGNI in your trash.\n" +
                "@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it and this SIGNI get --12000 power each.\n" +
                "@E: Put the top card of your deck into the trash. If that card is not a <<Virtual>> SIGNI, down this SIGNI."
        );

		setName("zh_simplified", "2434代号 笹木咲");
        setDescription("zh_simplified", 
                "@C :你的回合期间，这只精灵的力量依据你的废弃区的<<バーチャル>>精灵的数量，每有5张就+1000。\n" +
                "@U :当这只精灵攻击时，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其和这只精灵的力量各-12000。\n" +
                "@E 你的牌组最上面的牌放置到废弃区。那张牌不是<<バーチャル>>精灵的场合，这只精灵#D。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(this::onConstEffModGetValue));
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 1000 * (new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromTrash().getValidTargetsCount() / 5);
        }

        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
                gainPower(getCardIndex(), -12000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            CardIndex cardIndex = millDeck(1).get();
            
            if(cardIndex == null ||
               !CardType.isSIGNI(cardIndex.getIndexedInstance().getTypeByRef()) ||
               !cardIndex.getIndexedInstance().getSIGNIClass().matches(CardSIGNIClass.VIRTUAL))
            {
                down();
            }
        }
    }
}
